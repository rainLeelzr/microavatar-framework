package microavatar.framework.perm.fegin;

import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.fegin.fallback.UserFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@FeignClient(name = "avatar-perm", fallback = UserFallback.class)
public interface UserFegin {

    @RequestMapping("/perm/user/name/{name}")
    User getByName(@PathVariable(value = "name") String name);
}
