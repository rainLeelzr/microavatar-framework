package microavatar.framework.core.support.sequence;


/**
 * 获取序列
 *
 * @author Rain
 */
public interface Sequence<T> {

    /**
     * 获取序列
     */
    T next();
}
