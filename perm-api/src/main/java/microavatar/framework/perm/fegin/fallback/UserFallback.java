package microavatar.framework.perm.fegin.fallback;

import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.fegin.UserFegin;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserFegin {

    @Override
    public User getByName(String name) {
        return null;
    }
}
