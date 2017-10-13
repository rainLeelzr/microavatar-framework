package microavatar.framework.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * protobuf表格的请求时，需要用到的辅助参数
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protobuf {

    /**
     * 需要解析成的protobuf对应java对象的限定类名
     */
    String c2s() default "";

    /**
     * protobuf对应java对象的限定类名
     * 如果需要自动封装响应信息给客户端时，请填写此值
     */
    String s2c() default "";

}
