/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/user")
public class UserController extends BaseController<UserService, UserDao, User> {

    @Resource
    private UserService userService;

    @Override
    protected UserService getService() {
        return userService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public User getById(@PathVariable String id) {
        return super.getById(id);
    }

}