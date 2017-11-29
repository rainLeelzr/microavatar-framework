package microavatar.framework.security;

import microavatar.framework.security.auth.service.AvatarUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author Administrator
 */
@Component
public class AvatarAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private AvatarUserDetailsService avatarUserDetailsService;

    /**
     * 添加超级管理员账号，只供开发人员使用
     */
    private static final String SUPER_ADMIN_USERNAME = "avatarSuperAdmin";
    private static final String SUPER_ADMIN_PASSWORD = "15918746779";
    private static final GrantedAuthority SUPER_ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority("ROLE_SUPER_ADMIN");
    private static final boolean ENABLE_SUPER_ADMIN = true;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = checkSuperAdmin(username, password);

        if (userDetails == null) {
            userDetails = checkUser(username, password);
        }

        if (!userDetails.isEnabled()) {
            throw new DisabledException("用户名[" + username + "]已停用。");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    private UserDetails checkUser(String username, String password) {
        UserDetails userDetails = avatarUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("没有此用户。");
        }

        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("用户名为[" + username + "]输入的密码错误。");
        }

        return userDetails;
    }

    private UserDetails checkSuperAdmin(String username, String password) {
        UserDetails userDetail = null;
        if (SUPER_ADMIN_USERNAME.equals(username) && SUPER_ADMIN_PASSWORD.equals(password)) {
            userDetail = new org.springframework.security.core.userdetails.User(
                    SUPER_ADMIN_USERNAME,
                    SUPER_ADMIN_PASSWORD,
                    ENABLE_SUPER_ADMIN,
                    false,
                    false,
                    false,
                    Collections.singleton(SUPER_ADMIN_GRANTED_AUTHORITY)
            );
        }

        return userDetail;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
