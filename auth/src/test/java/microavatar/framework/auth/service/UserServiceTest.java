package microavatar.framework.auth.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.auth.dao.UserDao;
import microavatar.framework.auth.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends BaseServiceTestClass<User, UserDao, UserService> {

    @Resource(name = "userService")
    private UserService userService;

    @Override
    protected UserService getService() {
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