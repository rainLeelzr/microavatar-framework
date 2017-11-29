package microavatar.framework.security.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AvatarAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null || configAttributes.isEmpty()) {
            return;
        }

        // grantedAuthority 和 configAttributes需要保证字符串已经trim。这里就不再进行trim后再比较是否相等
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            for (ConfigAttribute configAttribute : configAttributes) {
                if (grantedAuthority.equals(configAttribute)) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足（该用户没有拥有必要的角色），无法访问资源！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
