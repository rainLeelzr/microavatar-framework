package microavatar.framework.auth.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Value("${auth.noInterceptUrls}")
    // private List<String> noInterceptUrl;
    private String[] noInterceptUrls;

    @Value("${auth.interceptUrls}")
    private List<String> interceptUrls;

    @Value("${auth.loginUrl}")
    private String loginUrl;

    @Value("${auth.logoutUrl}")
    private String logoutUrl;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        /*
          配置不会被拦截的链接 顺序判断
          authc:所有url都必须认证通过才可以访问;
          anon:所有url都都可以匿名访问
         */
        for (String noInterceptUrl : noInterceptUrls) {
            filterChainDefinitionMap.put(noInterceptUrl, "anon");
        }

        // // 静态资源
        // filterChainDefinitionMap.put("/static/**", "anon");
        //
        // // 首页
        // filterChainDefinitionMap.put("/", "anon");
        // filterChainDefinitionMap.put("/index.html", "anon");
        //
        // // 登录页面
        // filterChainDefinitionMap.put("/login.html", "anon");
        //
        // // 登录接口
        // filterChainDefinitionMap.put("/auth/login", "anon");

        // 登出接口
        // filterChainDefinitionMap.put("/auth/logout", "anon");

        // 登出接口，其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put(logoutUrl, "logout");

        // 其余的资源，都需要权限才能访问
        for (String interceptUrl : interceptUrls) {
            filterChainDefinitionMap.put(interceptUrl, "authc");
        }

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(loginUrl);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(AvatarShiroRealm avatarShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(avatarShiroRealm);
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
