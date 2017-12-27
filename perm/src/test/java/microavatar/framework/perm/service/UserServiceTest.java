package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends BaseTransactionalServiceTestClass<User, UserDao, UserService> {

    @Resource(name = "userService")
    private UserService userService;

    @Override
    public UserService getService() {
        return userService;
    }

    @Override
    public User genEntity() {
        return new User().randomEntity();
    }

}