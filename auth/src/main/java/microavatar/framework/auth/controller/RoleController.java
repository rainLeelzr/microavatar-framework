/***********************************************************************
 * @模块: 角色业务逻辑实现
 * @模块说明: 角色模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.RoleDao;
import microavatar.framework.auth.entity.Role;
import microavatar.framework.auth.service.RoleService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/role")
public class RoleController extends BaseController<RoleService, RoleDao, Role> {

    @Resource
    private RoleService roleService;

    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public Role getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}