/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明:用户模块服务
 * @作者: Administrator
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.service.UserService;
import microavatar.framework.perm.service.UserServiceExt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/perm/user")
public class UserControllerExt extends BaseController<UserService, UserDao, User> {

    @Resource
    private UserServiceExt userServiceExt;

    @Override
    protected UserServiceExt getService() {
        return userServiceExt;
    }

    @RequestMapping("/name/{name}")
    public User getByName(@PathVariable String name) {
        return userServiceExt.getByAccount(name);
    }

}