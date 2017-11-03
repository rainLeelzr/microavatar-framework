package microavatar.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            logoutSuccessUrl(String logoutSuccessUrl):
                The URL to redirect to after logout has occurred. The default is /login?logout
         */
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureForwardUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login/logout")// The URL to redirect to after logout has occurred. The default is /login?logout
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 添加超级管理员账号，只供开发人员使用
        auth
                .inMemoryAuthentication()
                .withUser("avatar").password("123456").roles("superAdmin");
    }
}
