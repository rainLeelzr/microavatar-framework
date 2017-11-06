/***********************************************************************
 * @模块: 系统权限业务逻辑实现
 * @模块说明: 系统权限模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.SysPermission;
import microavatar.framework.perm.dao.SysPermissionDao;
import microavatar.framework.perm.service.SysPermissionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/sysPermission")
public class SysPermissionController extends BaseController<SysPermissionService, SysPermissionDao, SysPermission> {

    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    protected SysPermissionService getService() {
        return sysPermissionService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public SysPermission getById(@PathVariable String id) {
        return super.getById(id);
    }

}