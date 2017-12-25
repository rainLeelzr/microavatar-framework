/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明:用户模块服务
 * @作者: Administrator
 ***********************************************************************/

package microavatar.framework.perm.controller;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User;
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
import java.util.Date;

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