/***********************************************************************
 * @模块: 角色权限业务逻辑实现
 * @模块说明: 角色权限模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.RoleSysPermissionDao;
import microavatar.framework.auth.entity.RoleSysPermission;
import microavatar.framework.auth.service.RoleSysPermissionService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/roleSysPermission")
public class RoleSysPermissionController extends BaseController<RoleSysPermissionService, RoleSysPermissionDao, RoleSysPermission> {

    @Resource
    private RoleSysPermissionService roleSysPermissionService;

    @Override
    protected RoleSysPermissionService getService() {
        return roleSysPermissionService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public RoleSysPermission getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}