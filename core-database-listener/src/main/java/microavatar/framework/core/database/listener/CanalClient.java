package microavatar.framework.core.database.listener;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CanalClient implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    @Value("${canal.server.ip:192.168.0.31}")
    private String canalServerIp;

    @Value("${canal.server.port:11111}")
    private int canalServerPort;

    /**
     * 支持正则表达式
     * 1. 所有表：.* or .*\\..*
     * 2. canal schema下所有表： canal\\..*
     * 3. canal下的以canal打头的表：canal\\.canal.*
     * 4. canal schema下的一张表：canal.test1
     * 5. 多个规则组合使用：canal\\..*,mysql.test1,mysql.test2 (逗号分隔)
     * 6  匹配多个类似库: canal_.*\\..*
     */
    @Value("${canal.server.subscribe:.*}")
    private String subscribe;

    /**
     * 断线重连相隔时间，毫秒
     */
    @Value("${canal.client.reconnectMills:5000}")
    private int reconnectMills;

    /**
     * 向canal服务器获取一次事件的相隔时间，毫秒
     */
    @Value("${canal.client.periodMills:1000}")
    private long periodMills;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private CanalConnector connector;

    private Set<EventBinding> eventBindings;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    private boolean isStart = false;

    private void start() {
        executor.submit(new CanalClientRunnable());
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("===============destroy");
        if (connector != null) {
            connector.disconnect();
        }
    }

    private void loopGetMessage() {
        int batchSize = 10000;

        while (true) {
            Message message = connector.getWithoutAck(batchSize, periodMills, TimeUnit.MILLISECONDS);
            long batchId = message.getId();
            int size = message.getEntries().size();
            log.trace("接受到{}条数据库事件，batchId:{}", size, batchId);
            try {
                if (batchId != -1 && size > 0) {
                    processEntries(message.getEntries());
                }
                // 提交确认
                connector.ack(batchId);
            } catch (Exception e) {
                // 处理失败, 回滚数据
                connector.rollback(batchId);

                log.error(e.getMessage(), e);
            }
        }
    }

    private void processEntries(List<Entry> entries) {
        for (Entry entry : entries) {
            // 过滤事务开始与结束的事件
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChange = null;
            try {
                rowChange = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("解析bonlog事件错误 , data:" + entry.toString(), e);
            }

            EventType eventType = rowChange.getEventType();
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();

            if (log.isTraceEnabled()) {
                log.trace(formatEventDetail(eventType, entry, rowChange));
            }

            publishEvent(schemaName, tableName, eventType, entry, rowChange);
        }
    }

    private String formatEventDetail(EventType eventType, Entry entry, RowChange rowChange) {
        StringBuilder sb = new StringBuilder();
        sb.append("================> binlog[").append(entry.getHeader().getLogfileName())
                .append(":")
                .append(entry.getHeader().getLogfileOffset())
                .append("] , name[")
                .append(entry.getHeader().getSchemaName())
                .append(",")
                .append(entry.getHeader().getTableName())
                .append("] , eventType : ")
                .append(eventType);

        for (RowData rowData : rowChange.getRowDatasList()) {
            if (eventType == EventType.DELETE) {
                for (Column column : rowData.getBeforeColumnsList()) {
                    sb.append("\n").append(column.getName()).append(": ").append(column.getValue()).append("    update=").append(column.getUpdated());
                }
            } else if (eventType == EventType.INSERT) {
                for (Column column : rowData.getAfterColumnsList()) {
                    sb.append("\n").append(column.getName()).append(": ").append(column.getValue()).append("    update=").append(column.getUpdated());
                }
            } else {
                sb.append("\n").append("-------> before");
                for (Column column : rowData.getBeforeColumnsList()) {
                    sb.append("\n").append(column.getName()).append(": ").append(column.getValue()).append("    update=").append(column.getUpdated());
                }
                System.out.println();
                sb.append("\n").append("-------> after");
                for (Column column : rowData.getAfterColumnsList()) {
                    sb.append("\n").append(column.getName()).append(": ").append(column.getValue()).append("    update=").append(column.getUpdated());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 发布事件给spring容器
     * <p>
     * 根据数据库名、表名、canal事件类型，找到已经注册过的spring事件，并发布spring事件
     */
    private void publishEvent(String schemaName, String tableName, EventType eventType, Entry entry, RowChange rowChange) {
        if (this.eventBindings == null) {
            return;
        }

        for (EventBinding eventBinding : this.eventBindings) {
            DatabaseEvent databaseEvent;

            if (!eventBinding.isAlwaysPublish()) {
                // 如果需要检查数据库名，则检查
                if (eventBinding.isCheckSchemaName()) {
                    if (!eventBinding.getSchemaName().equalsIgnoreCase(schemaName)) {
                        continue;
                    }
                }

                // 如果需要检查表名，则检查
                if (eventBinding.isCheckTableName()) {
                    if (!eventBinding.getTableName().equalsIgnoreCase(tableName)) {
                        continue;
                    }
                }

                // 如果需要检查canal事件类型，则检查
                if (eventBinding.isCheckEventType()) {
                    if (eventBinding.getEventType() != eventType) {
                        continue;
                    }
                }
            }

            // 通过所有检查，发布事件
            try {
                databaseEvent = genDatabaseEvent(eventBinding.getDatabaseEventClass(), entry, rowChange);
            } catch (Exception e) {
                log.error("创建spring event失败，取消发布数据库事件", e);
                return;
            }
            try {
                applicationEventPublisher.publishEvent(databaseEvent);
            } catch (Exception e) {
                log.error("业务逻辑处理异常，请检查并手动处理事件。", e);
                log.error("事件详细：");
                log.error(JsonFormat.printToString(entry));
                log.error(JsonFormat.printToString(rowChange));
            }
        }
    }

    private DatabaseEvent genDatabaseEvent(Class<? extends DatabaseEvent> databaseEventClass, Entry entry, RowChange rowChange)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        DatabaseEvent databaseEvent = databaseEventClass.getConstructor(Entry.class, RowChange.class).newInstance(entry, rowChange);
        databaseEvent.setEntry(entry);
        databaseEvent.setRowChange(rowChange);
        return databaseEvent;
    }

    /**
     * 添加事件监听，过滤重复事件
     */
    public void addEventListener(String schemaName, String tableName, EventType canalEventType, Class<? extends DatabaseEvent> databaseEventClass) {
        Assert.notNull(databaseEventClass, "databaseEventClass 不能为null");

        EventBinding.Builder builder = new EventBinding.Builder().setDatabaseEventClass(databaseEventClass);
        if (StringUtils.isNotBlank(schemaName)) {
            builder.setSchemaName(schemaName);
        }
        if (StringUtils.isNotBlank(tableName)) {
            builder.setTableName(tableName);
        }
        if (canalEventType != null) {
            builder.setEventType(canalEventType);
        }
        if (this.eventBindings == null) {
            this.eventBindings = new HashSet<>();
        }
        EventBinding eventBinding = builder.build();
        this.eventBindings.add(eventBinding);
        log.info("注册数据库事件：{}，总注册了{}个事件", databaseEventClass, this.eventBindings.size());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!this.isStart) {
            this.isStart = true;
            start();
        }
    }

    private class CanalClientRunnable implements Runnable {

        @Override
        public void run() {
            // 创建链接
            connector = CanalConnectors.newSingleConnector(
                    new InetSocketAddress(canalServerIp, canalServerPort), "example", "", "");

            Runtime.getRuntime().addShutdownHook(new Thread(connector::disconnect));
            log.info("正在连接canalServer：{}:{},subscribe:{}", canalServerIp, canalServerPort, subscribe);
            try {
                connector.connect();
                connector.subscribe(subscribe);

                connector.rollback();
                log.info("canalClient启动成功，正在监听的canalServerIp：{}:{}", canalServerIp, canalServerPort);
                loopGetMessage();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                connector.disconnect();
            }

            log.info("连接canalServer：{}:{} 失败，{}毫秒后重试", canalServerIp, canalServerPort, reconnectMills);
            try {
                Thread.sleep(reconnectMills);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            // 此处应该提交新任务，而不能用死循环的方式来重连，因为采用死循环重连方案，以后用了热更技术，就会有越来越多的runnable执行死循环
            executor.submit(new CanalClientRunnable());
        }
    }
}
