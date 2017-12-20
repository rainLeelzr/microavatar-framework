/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明:用户模块服务
 * @作者: Administrator
 ***********************************************************************/

package microavatar.framework.perm.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.database.SqlCondition;
import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.entity.User2;
import microavatar.framework.perm.service.User2Service;
import microavatar.framework.perm.service.UserService;
import microavatar.framework.perm.service.UserServiceExt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/perm/user")
@Slf4j
public class UserControllerExt extends BaseController<UserService, UserDao, User> {

    @Resource
    private UserServiceExt userServiceExt;

    @Resource
    private UserService userService;

    @Resource
    private User2Service user2Service;

    @Override
    protected UserServiceExt getService() {
        return userServiceExt;
    }

    @RequestMapping("/name/{name}")
    public User getByName(@PathVariable String name) {
        return userServiceExt.getByAccount(name);
    }

    @GetMapping("/t1")
    public void testInsertAndCanal() {
        int insertCount = 100000;

        LocalDateTime insertStartLocalDate = LocalDateTime.now();
        long insertStart = System.currentTimeMillis();

        for (int i = 0; i < insertCount; i++) {
            userServiceExt.add(genEntity());
        }

        long insertEnd = System.currentTimeMillis();
        LocalDateTime insertEndLocalDate = LocalDateTime.now();
        log.info("开始插入时间：{}，结束插入时间：{}", insertStartLocalDate, insertEndLocalDate);
        log.info("插入耗时：{}ms", insertEnd - insertStart);

    }

    @GetMapping("/t2")
    public void testBatchInsertAndCanal() {
        int insertCount = 100000;
        List<User> users = new ArrayList<>(10000);

        for (int i = 0; i < insertCount; i++) {
            users.add(genEntity());
        }
        LocalDateTime insertStartLocalDate = LocalDateTime.now();
        long insertStart = System.currentTimeMillis();
        userServiceExt.batchAdd(users);
        long insertEnd = System.currentTimeMillis();
        LocalDateTime insertEndLocalDate = LocalDateTime.now();
        log.info("开始插入时间：{}，结束插入时间：{}", insertStartLocalDate, insertEndLocalDate);
        log.info("插入耗时：{}ms", insertEnd - insertStart);

    }

    @GetMapping("/t3")
    public void testCount() {
        List<User> users = userService.findPage(new SqlCondition().build());
        List<User2> user2s = user2Service.findPage(new SqlCondition().build());
        log.info("user2.size = {}", user2s.size());

        List<User2Ext> user2Exts = new ArrayList<>(user2s.size());
        for (User user : users) {
            for (User2 user2 : user2s) {
                if (user.getId().equals(user2.getId())) {
                    User2Ext user2Ext = new User2Ext();
                    user2Ext.setId(user.getId());
                    user2Ext.setCost(user2.getTimeVersion().getTime() - user.getTimeVersion().getTime());
                    user2Exts.add(user2Ext);
                    break;
                }
            }
        }

        log.info("user2Ext.size = {}", user2Exts.size());

        Map<Long, List<User2Ext>> collect = user2Exts.parallelStream()
                .collect(Collectors.groupingBy(User2Ext::getCost, Collectors.toList()));

        Map<Long, Integer> count = new HashMap<>(collect.size() * 2);
        collect.forEach((time, list) -> {
            count.put(time, list.size());
        });

        count.forEach((time, size) -> log.info("{} {}", time, size));
    }

    @Getter
    @Setter
    public static class User2Ext extends User2 {
        private long cost;
    }

    public User genEntity() {
        User entity = new User();
        entity.setAccount(RandomStringUtils.randomNumeric(5));
        entity.setPwd(RandomStringUtils.randomNumeric(5));
        entity.setCreateTime(RandomUtils.nextLong());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setStatus((byte) RandomUtils.nextInt(1, 5));
        entity.setTimeVersion(new Date());
        return entity;
    }
}