/***********************************************************************
 * @模块: 角色权限业务逻辑实现
 * @模块说明: 角色权限模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.RoleSysPermission;
import microavatar.framework.perm.dao.RoleSysPermissionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleSysPermissionService extends BaseService<RoleSysPermission, RoleSysPermissionDao> {

    @Resource
    private RoleSysPermissionDao roleSysPermissionDao;

    @Override
    protected RoleSysPermissionDao getDao() {
        return roleSysPermissionDao;
    }

}