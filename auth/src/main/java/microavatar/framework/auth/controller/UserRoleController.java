/***********************************************************************
 * @模块: 用户角色业务逻辑实现
 * @模块说明: 用户角色模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.UserRoleDao;
import microavatar.framework.auth.entity.UserRole;
import microavatar.framework.auth.service.UserRoleService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/userRole")
public class UserRoleController extends BaseController<UserRoleService, UserRoleDao, UserRole> {

    @Resource
    private UserRoleService userRoleService;

    @Override
    protected UserRoleService getService() {
        return userRoleService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserRole getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}