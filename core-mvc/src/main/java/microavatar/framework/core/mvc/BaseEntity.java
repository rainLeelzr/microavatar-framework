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

    /**
     * 主键 id
     * bigint
     */
    protected Long id;

    /**
     * 时间版本
     * insert、update时，需要设置为系统当前时间戳
     */
    protected Long timeVersion;

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
        return RandomStringUtils.randomNumeric(5);
    }

    protected Byte randomByte() {
        return (byte) RandomUtils.nextInt(0, 1);
    }

    protected Integer randomInteger() {
        return RandomUtils.nextInt(1, 5);
    }

    protected Long randomLong() {
        return RandomUtils.nextLong(1, 5);
    }
}
