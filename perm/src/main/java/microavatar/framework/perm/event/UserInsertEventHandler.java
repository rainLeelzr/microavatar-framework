package microavatar.framework.perm.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.database.listener.CanalClient;
import microavatar.framework.perm.entity.User2;
import microavatar.framework.perm.service.User2Service;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Rain
 */
@Component
@Slf4j
public class UserInsertEventHandler implements ApplicationListener<UserInsertEvent>, InitializingBean {

    @Resource
    private CanalClient canalClient;

    @Resource
    private User2Service user2Service;

    /**
     * 监听的数据库中被执行了一条sql，就会触发一次本事件。
     * <p>
     * 被执行的那一条sql，如果是单实体insert，则rowChange.getRowDatasList().size() = 1;
     * 如果是批量插入，例如insert xxx (x,x) values (x1, x2),(x1, x2)
     * 则rowChange.getRowDatasList().size = 2
     */
    @Override
    public void onApplicationEvent(UserInsertEvent event) {
        CanalEntry.RowChange rowChange = event.getRowChange();
        List<User2> user2s = new ArrayList<>(rowChange.getRowDatasCount());

        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            User2 user2 = new User2();
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                boolean isNull = column.getIsNull();

                if (!isNull && column.getName().equalsIgnoreCase(User2.ID)) {
                    user2.setId(Long.valueOf(column.getValue()));
                } else if (!isNull && column.getName().equalsIgnoreCase(User2.ACCOUNT)) {
                    user2.setAccount(column.getValue());
                } else if (!isNull && column.getName().equalsIgnoreCase(User2.PWD)) {
                    user2.setPwd(column.getValue());
                } else if (!isNull && column.getName().equalsIgnoreCase(User2.CREATETIME)) {
                    user2.setCreateTime(Long.valueOf(column.getValue()));
                } else if (!isNull && column.getName().equalsIgnoreCase(User2.NAME)) {
                    user2.setName(column.getValue());
                } else if (!isNull && column.getName().equalsIgnoreCase(User2.STATUS)) {
                    user2.setStatus(Byte.valueOf(column.getValue()));
                }
                user2.setTimeVersion(new Date());
            }
            user2s.add(user2);
        }
        user2Service.batchAdd(user2s);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (canalClient != null) {
            canalClient.addEventListener(null, "auth_user", CanalEntry.EventType.INSERT, UserInsertEvent.class);
        }
    }
}
