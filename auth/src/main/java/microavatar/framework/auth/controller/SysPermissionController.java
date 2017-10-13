/***********************************************************************
 * @模块: 系统权限业务逻辑实现
 * @模块说明: 系统权限模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.SysPermissionDao;
import microavatar.framework.auth.entity.SysPermission;
import microavatar.framework.auth.service.SysPermissionService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/sysPermission")
public class SysPermissionController extends BaseController<SysPermissionService, SysPermissionDao, SysPermission> {

    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    protected SysPermissionService getService() {
        return sysPermissionService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public SysPermission getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}