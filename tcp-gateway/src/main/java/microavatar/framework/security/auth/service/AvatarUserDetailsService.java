package microavatar.framework.security.auth.service;

import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.status.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author Administrator
 */
@Service
public class AvatarUserDetailsService implements UserDetailsService {

    @Resource
    private UserServiceExt userServiceExt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceExt.getByAccount(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserDetails userDetail =
                new org.springframework.security.core.userdetails.User(
                        username,
                        user.getPwd(),
                        user.getStatus() == UserStatus.ENABLE.getId(),
                        false,
                        false,
                        false,
                        Collections.emptyList()
                );

        return userDetail;
    }
}
