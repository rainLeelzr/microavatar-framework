/***********************************************************************
 * @模块: 系统权限业务逻辑实现
 * @模块说明: 系统权限模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.SysPermission;
import microavatar.framework.perm.dao.SysPermissionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPermissionService extends BaseService<SysPermission, SysPermissionDao> {

    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    protected SysPermissionDao getDao() {
        return sysPermissionDao;
    }

}