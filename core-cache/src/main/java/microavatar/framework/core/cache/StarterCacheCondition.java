package microavatar.framework.core.cache;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 启动avatarCache功能的条件
 */
public class StarterCacheCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(context.getEnvironment(), "spring.cache.");
        String env = resolver.getProperty("custom-type");
        if (env == null) {
            return false;
        }
        return "avatarCache".equalsIgnoreCase(env.toLowerCase());
    }
}
