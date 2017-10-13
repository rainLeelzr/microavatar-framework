package microavatar.framework.core.net.tcp;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 启动TcpServer的条件
 */
public class TcpServerCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(context.getEnvironment(), "avatar.tcpServer.");
        String env = resolver.getProperty("enable");
        if (env == null) {
            return false;
        }
        return "true".equalsIgnoreCase(env.toLowerCase());
    }
}
