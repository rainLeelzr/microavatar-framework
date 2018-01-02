/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明:用户模块服务
 * @作者: Administrator
 ***********************************************************************/

package microavatar.framework.perm.controller;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.service.UserService;
import microavatar.framework.perm.service.UserServiceExt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/perm/user")
@Slf4j
public class UserControllerExt{

    @Resource
    private UserServiceExt userServiceExt;

    @Resource
    private UserService userService;

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
            userService.add(new User().randomEntity());
        }

        long insertEnd = System.currentTimeMillis();
        LocalDateTime insertEndLocalDate = LocalDateTime.now();
        log.info("开始插入时间：{}，结束插入时间：{}", insertStartLocalDate, insertEndLocalDate);
        log.info("插入耗时：{}ms", insertEnd - insertStart);

    }

}