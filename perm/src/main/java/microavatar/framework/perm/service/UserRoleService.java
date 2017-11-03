/***********************************************************************
 * @模块: 用户角色业务逻辑实现
 * @模块说明: 用户角色模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.UserRole;
import microavatar.framework.perm.dao.UserRoleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleService extends BaseService<UserRole, UserRoleDao> {

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    protected UserRoleDao getDao() {
        return userRoleDao;
    }

}