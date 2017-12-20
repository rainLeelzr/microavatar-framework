package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.dao.UserDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
        User entity = new User();
        entity.setAccount(RandomStringUtils.randomNumeric(5));
        entity.setPwd(RandomStringUtils.randomNumeric(5));
        entity.setCreateTime(RandomUtils.nextLong());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setStatus((byte) RandomUtils.nextInt(1, 5));
        return entity;
    }

}