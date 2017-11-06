/***********************************************************************
 * @模块: 用户角色业务逻辑实现
 * @模块说明: 用户角色模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.UserRole;
import microavatar.framework.perm.dao.UserRoleDao;
import microavatar.framework.perm.service.UserRoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/userRole")
public class UserRoleController extends BaseController<UserRoleService, UserRoleDao, UserRole> {

    @Resource
    private UserRoleService userRoleService;

    @Override
    protected UserRoleService getService() {
        return userRoleService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserRole getById(@PathVariable String id) {
        return super.getById(id);
    }

}