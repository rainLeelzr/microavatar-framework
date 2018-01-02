package microavatar.framework.perm.fegin;

import microavatar.framework.perm.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@FeignClient(name = "avatar-perm", fallback = UserFegin.UserFallback.class)
public interface UserFegin {

    @RequestMapping("/perm/user/name/{name}")
    User getByName(@PathVariable(value = "name") String name);

    @Component
    class UserFallback implements UserFegin {

        @Override
        public User getByName(String name) {
            return null;
        }
    }
}
