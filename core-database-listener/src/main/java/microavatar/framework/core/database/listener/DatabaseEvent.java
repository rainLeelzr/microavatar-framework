package microavatar.framework.core.database.listener;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import org.springframework.context.ApplicationEvent;

public abstract class DatabaseEvent extends ApplicationEvent {

    private Entry entry;

    private RowChange rowChange;

    public DatabaseEvent(Entry entry, RowChange rowChage) {
        super(entry);

        this.entry = entry;
        this.rowChange = rowChage;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public RowChange getRowChange() {
        return rowChange;
    }

    public void setRowChange(RowChange rowChange) {
        this.rowChange = rowChange;
    }
}
