package microavatar.framework.core.mvc;

import microavatar.framework.core.pk.PkGenerator;

import java.io.Serializable;

/**
 * @author Administrator
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 主键 id
     * varchar(36)
     */
    protected String id;

    public static final String ID = "id";

    public static final String IDS = "ids";

    public static final String ALL_COLUMNS = "_all_columns";

    public String generateId() {
        this.id = PkGenerator.getPk();
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
