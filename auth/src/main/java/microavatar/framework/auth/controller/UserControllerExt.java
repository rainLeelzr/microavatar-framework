/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明:用户模块服务
 * @作者: Administrator
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.UserDao;
import microavatar.framework.auth.entity.User;
import microavatar.framework.auth.service.UserService;
import microavatar.framework.auth.service.UserServiceExt;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/user")
public class UserControllerExt extends BaseController<UserService, UserDao, User> {

    @Resource
    private UserServiceExt userServiceExt;

    @Override
    protected UserServiceExt getService() {
        return userServiceExt;
    }

    @RequestMapping("/name/{name}")
    public User getEntityByName(@PathVariable String name) {
        return userServiceExt.getByAccount(name);
    }


}