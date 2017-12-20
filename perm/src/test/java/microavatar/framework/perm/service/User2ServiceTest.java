package microavatar.framework.perm.service;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.Application;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.entity.User2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class User2ServiceTest extends AbstractJUnit4SpringContextTests {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "user2Service")
    private User2Service user2Service;

    public User2 genEntity() {
        User2 entity = new User2();
        entity.setAccount(RandomStringUtils.randomNumeric(5));
        entity.setPwd(RandomStringUtils.randomNumeric(5));
        entity.setCreateTime(RandomUtils.nextLong());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setStatus((byte) RandomUtils.nextInt(1, 5));
        return entity;
    }

    @Test
    public void testInsertAndCanal() {
        int insertCount = 1000;
        UserServiceTest userServiceTest = new UserServiceTest();

        LocalDateTime insertStartLocalDate = LocalDateTime.now();
        long insertStart = System.currentTimeMillis();

        for (int i = 0; i < insertCount; i++) {
            userService.add(userServiceTest.genEntity());
        }

        long insertEnd = System.currentTimeMillis();
        LocalDateTime insertEndLocalDate = LocalDateTime.now();
        log.info("开始插入时间：{}，结束插入时间：{}", insertStartLocalDate, insertEndLocalDate);
        log.info("插入耗时：{}ms", insertEnd - insertStart);

    }

    @Test
    public void testBatchInsertAndCanal() {
        int insertCount = 2;
        UserServiceTest userServiceTest = new UserServiceTest();
        List<User> users = new ArrayList<>(10000);

        for (int i = 0; i < insertCount; i++) {
            users.add(userServiceTest.genEntity());
        }
        LocalDateTime insertStartLocalDate = LocalDateTime.now();
        long insertStart = System.currentTimeMillis();
        userService.batchAdd(users);
        long insertEnd = System.currentTimeMillis();
        LocalDateTime insertEndLocalDate = LocalDateTime.now();
        log.info("开始插入时间：{}，结束插入时间：{}", insertStartLocalDate, insertEndLocalDate);
        log.info("插入耗时：{}ms", insertEnd - insertStart);

    }

}