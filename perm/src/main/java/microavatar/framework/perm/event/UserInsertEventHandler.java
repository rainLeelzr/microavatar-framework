package microavatar.framework.perm.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.database.listener.CanalClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Rain
 */
@Component
@Slf4j
public class UserInsertEventHandler implements ApplicationListener<UserInsertEvent>, InitializingBean {

    @Autowired(required = false)
    private CanalClient canalClient;

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
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (canalClient != null) {
            canalClient.addEventListener(null, "auth_user", CanalEntry.EventType.INSERT, UserInsertEvent.class);
        }
    }
}
