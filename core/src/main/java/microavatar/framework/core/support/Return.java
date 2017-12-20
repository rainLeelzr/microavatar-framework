package microavatar.framework.core.support;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 方法、接口调用的返回结果
 *
 * @author Rain
 */
@Getter
@Setter
@Builder
public class Return<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 描述信息
     */
    private String errorMsg;

    /**
     * 详细信息
     */
    private String errorDetail;

    /**
     * 调用接口得到的数据
     */
    private T data;

    public static <T> Return success(T data) {
        return new ReturnBuilder<T>().success(true).data(data).build();
    }

    public static <T> Return fail(T data) {
        return new ReturnBuilder<T>().success(false).data(data).build();
    }
}
