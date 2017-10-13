/***********************************************************************
 * @模块: 用户角色业务逻辑实现
 * @模块说明: 用户角色模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.UserRoleDao;
import microavatar.framework.auth.entity.UserRole;
import microavatar.framework.common.BaseService;
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