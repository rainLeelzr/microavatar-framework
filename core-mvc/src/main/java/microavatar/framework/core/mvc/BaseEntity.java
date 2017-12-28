package microavatar.framework.core.mvc;

import lombok.Getter;
import lombok.Setter;
import microavatar.framework.core.support.sequence.impl.LongSequence;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;

/**
 * @author rain
 */
@Getter
@Setter
public abstract class BaseEntity<E> implements Serializable {

    public static final String ID = "id";
    public static final String IDS = "ids";
    public static final String CREATE_TIME = "create_time";
    public static final String MODIFY_TIME = "modify_time";
    public static final String DELETED = "id_deleted";

    /**
     * 主键 id
     *
     * @dbColumnName id
     * @dbColumnType bigint(20)
     */
    protected Long id;

    /**
     * 创建时间戳
     *
     * @dbColumnName create_time
     * @dbColumnType bigint(20)
     */
    protected Long createTime;

    /**
     * 最后修改时间戳
     * insert、update时，需要设置为系统当前时间戳
     *
     * @dbColumnName modify_time
     * @dbColumnType bigint(20)
     */
    protected Long modifyTime;

    /**
     * 是否删除
     *
     * @dbColumnName is_deleted
     * @dbColumnType tinyint(1)
     */
    protected Boolean deleted;

    /**
     * 生成随机的entity
     * 所有字段都随机赋值
     */
    abstract public E randomEntity();

    /**
     * 生成新的id
     */
    public Long randomId() {
        this.id = LongSequence.get();
        return this.id;
    }

    protected String randomString() {
        return RandomStringUtils.randomGraph(5);
    }

    protected Byte randomByte() {
        return (byte) RandomUtils.nextInt(0, 6);
    }

    protected Boolean randomBoolean() {
        return RandomUtils.nextBoolean();
    }

    protected Integer randomInteger() {
        return RandomUtils.nextInt(1, 5);
    }

    protected Long randomLong() {
        return RandomUtils.nextLong(1, 5);
    }
}
