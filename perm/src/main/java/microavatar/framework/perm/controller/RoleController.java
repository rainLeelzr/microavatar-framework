/***********************************************************************
 * @模块: 角色业务逻辑实现
 * @模块说明: 角色模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.Role;
import microavatar.framework.perm.dao.RoleDao;
import microavatar.framework.perm.service.RoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/perm/role")
public class RoleController extends BaseController<RoleService, RoleDao, Role> {

    @Resource
    private RoleService roleService;

    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public Role getById(@PathVariable String id) {
        return super.getById(id);
    }

}