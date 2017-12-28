package microavatar.framework.perm.service;

import microavatar.framework.BaseCommitServiceTestClass;
import microavatar.framework.perm.criteria.UserCriteria;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends BaseCommitServiceTestClass<UserCriteria, UserDao, User, UserService> {

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

    @Override
    public UserCriteria getCriteria() {
        return UserCriteria.builder().build();
    }

}