package microavatar.framework.core.database.listener;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

/**
 * @author Rain
 */
@Getter
@Setter
public class EventBinding {

    /**
     * 是否忽略所有条件，始终发布本事件
     */
    private boolean alwaysPublish = true;

    /**
     * 数据库名
     */
    private String schemaName;

    /**
     * 是否需要匹配schemaName
     */
    private boolean checkSchemaName = false;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 是否需要匹配tableName
     */
    private boolean checkTableName = false;

    /**
     * canal事件
     */
    private EventType eventType;

    /**
     * 是否需要匹配eventType
     */
    private boolean checkEventType = false;

    /**
     * 匹配成功后，需要发布的事件
     */
    private Class<? extends DatabaseEvent> databaseEventClass;

    private EventBinding() {
    }

    public static class Builder {

        private EventBinding eventBinding;

        public Builder() {
            this.eventBinding = new EventBinding();
        }

        public Builder setSchemaName(String schemaName) {
            Assert.hasText(schemaName, "schemaName 不能为空");

            this.eventBinding.schemaName = schemaName;
            this.eventBinding.checkSchemaName = true;
            this.eventBinding.alwaysPublish = false;

            return this;
        }

        public Builder setTableName(String tableName) {
            Assert.hasText(tableName, "tableName 不能为空");

            this.eventBinding.tableName = tableName;
            this.eventBinding.checkTableName = true;
            this.eventBinding.alwaysPublish = false;

            return this;
        }

        public Builder setEventType(EventType eventType) {
            Assert.notNull(eventType, "eventType 不能为null");

            this.eventBinding.eventType = eventType;
            this.eventBinding.checkEventType = true;
            this.eventBinding.alwaysPublish = false;

            return this;
        }

        public Builder setDatabaseEventClass(Class<? extends DatabaseEvent> databaseEventClass) {
            Assert.notNull(databaseEventClass, "databaseEventClass 不能为null");
            this.eventBinding.databaseEventClass = databaseEventClass;

            return this;
        }

        public EventBinding build() {
            return this.eventBinding;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EventBinding that = (EventBinding) o;

        return new EqualsBuilder()
                .append(alwaysPublish, that.alwaysPublish)
                .append(checkSchemaName, that.checkSchemaName)
                .append(checkTableName, that.checkTableName)
                .append(checkEventType, that.checkEventType)
                .append(schemaName, that.schemaName)
                .append(tableName, that.tableName)
                .append(eventType, that.eventType)
                .append(databaseEventClass, that.databaseEventClass)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(alwaysPublish)
                .append(schemaName)
                .append(checkSchemaName)
                .append(tableName)
                .append(checkTableName)
                .append(eventType)
                .append(checkEventType)
                .append(databaseEventClass)
                .toHashCode();
    }
}
