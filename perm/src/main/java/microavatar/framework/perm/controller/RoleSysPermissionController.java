/***********************************************************************
 * @模块: 角色权限业务逻辑实现
 * @模块说明: 角色权限模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.RoleSysPermission;
import microavatar.framework.perm.dao.RoleSysPermissionDao;
import microavatar.framework.perm.service.RoleSysPermissionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/roleSysPermission")
public class RoleSysPermissionController extends BaseController<RoleSysPermissionService, RoleSysPermissionDao, RoleSysPermission> {

    @Resource
    private RoleSysPermissionService roleSysPermissionService;

    @Override
    protected RoleSysPermissionService getService() {
        return roleSysPermissionService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public RoleSysPermission getById(@PathVariable String id) {
        return super.getById(id);
    }

}