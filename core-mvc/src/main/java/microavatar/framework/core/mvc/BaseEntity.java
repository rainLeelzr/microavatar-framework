package microavatar.framework.core.mvc;

import microavatar.framework.core.support.sequence.impl.LongSequence;

import java.io.Serializable;

/**
 * @author rain
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 主键 id
     * bigint
     */
    protected Long id;

    public static final String ID = "id";

    public static final String IDS = "ids";

    /**
     * 生成新的id
     */
    public Long newId() {
        this.id = LongSequence.get();
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
