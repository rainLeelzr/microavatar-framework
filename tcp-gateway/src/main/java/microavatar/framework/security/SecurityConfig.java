package microavatar.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "avatarAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Resource(name = "avatarFilterSecurityInterceptor")
    private Filter avatarFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            logoutSuccessUrl(String logoutSuccessUrl):
                The URL to redirect to after logout has occurred. The default is /login?logout
         */
        http
                .addFilterBefore(avatarFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .authorizeRequests().antMatchers("/", "/static/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .successHandler(new LoginSuccessHandler()).failureHandler(new LoginFailureHandler()).permitAll()
                .and()
                .logout().logoutSuccessUrl("/auth/logout").permitAll()
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
