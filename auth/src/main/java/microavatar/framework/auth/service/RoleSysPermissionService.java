/***********************************************************************
 * @模块: 角色权限业务逻辑实现
 * @模块说明: 角色权限模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.RoleSysPermissionDao;
import microavatar.framework.auth.entity.RoleSysPermission;
import microavatar.framework.common.BaseService;
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