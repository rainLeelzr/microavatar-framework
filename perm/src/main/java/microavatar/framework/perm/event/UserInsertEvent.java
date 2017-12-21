package microavatar.framework.perm.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import microavatar.framework.core.database.listener.DatabaseEvent;

public class UserInsertEvent extends DatabaseEvent {

    public UserInsertEvent(CanalEntry.Entry entry, CanalEntry.RowChange rowChange) {
        super(entry, rowChange);
    }
}
