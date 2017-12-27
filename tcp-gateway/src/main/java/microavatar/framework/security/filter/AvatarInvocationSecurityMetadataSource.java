package microavatar.framework.security.filter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class AvatarInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    /**
     * key: RequestMatcher，可以理解为url
     * value: 角色集合
     */
    private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;

    private Collection<ConfigAttribute> allAttribute = new HashSet<>();

    /**
     * 加载url资源和角色
     */
    private void loadResources() {
        //List<Role> roles = new ArrayList<>();
        //List<SysResource> resources = new ArrayList<>();
        //List<RoleResource> roleResources = new ArrayList<>();

        //resourceMap = new HashMap<>(roles.size());
        resourceMap = new HashMap<>();

        RequestMatcher matcher = new AntPathRequestMatcher("/auth/test");
        Collection<ConfigAttribute> configAttributes = new ArrayList<>(2);
        resourceMap.put(matcher, configAttributes);
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Collection<ConfigAttribute> matchAttributes = new HashSet<>();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                matchAttributes.addAll(entry.getValue());
            }
        }
        if (matchAttributes.size() > 0) {
            return new ArrayList<>(matchAttributes);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadResources();
    }
}
