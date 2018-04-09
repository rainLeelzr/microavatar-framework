package microavatar.framework.core.net.tcp.request.worker;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.request.Request;
import microavatar.framework.core.net.tcp.request.RequestHandler;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Rain
 */
@Slf4j
public class RequestHandleWorker extends Thread {

    private static final int MAX_QUEUE_SIZE = Math.min(1000, 1000);

    private volatile boolean running = true;

    private BlockingQueue<Request> blockingQueue;

    /**
     * 处理完的个数
     */
    private long handledTimes = 0;

    private List<RequestHandler> requestHandlers;

    public RequestHandleWorker(List<RequestHandler> requestHandlers,
                               String workName) {
        super(workName);
        this.requestHandlers = requestHandlers;
        this.blockingQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            Request request = null;
            try {
                request = this.blockingQueue.take();
                handledTimes++;

                if (request == null) {
                    log.error("null被放入到request请求队列中，请检查入队逻辑！");
                    continue;
                }

                String requestData = request.getPackageData().toString();
                log.debug("开始处理tcp请求：{}", requestData);

                for (RequestHandler requestHandler : requestHandlers) {
                    if (requestHandler.supports(request)) {
                        long start = System.currentTimeMillis();
                        requestHandler.handle(request);
                        long costTime = System.currentTimeMillis() - start;
                        log.debug("完成业务逻处理, {} 耗时={}ms", requestData, costTime);
                        break;
                    }
                }

            } catch (Exception e) {
                String requestData = "";
                if (request != null) {
                    requestData = request.getPackageData() == null ?
                            "request.getPackageData() = null" :
                            request.getPackageData().toString();
                }
                log.error("tcp请求处理错误: {}", requestData, e);
            }
        }
    }

    /**
     * 接收一个事件请求，放入队列中
     */
    public final void acceptRequest(Request request) {
        boolean ok = this.blockingQueue.offer(request);
        if (!ok) {
            log.error("添加请求到 请求队列 失败！");
        }
    }

    public void shutdown() {
        if (running) {
            running = false;
            //设置线程中断标志
            this.interrupt();
        }
    }

    public boolean isRunning() {
        return this.running && this.isAlive();
    }

}
