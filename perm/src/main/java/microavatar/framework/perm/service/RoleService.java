/***********************************************************************
 * @模块: 角色业务逻辑实现
 * @模块说明: 角色模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.Role;
import microavatar.framework.perm.dao.RoleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class RoleService extends BaseService<Role, RoleDao> {

    @Resource
    private RoleDao roleDao;

    @Override
    protected RoleDao getDao() {
        return roleDao;
    }

}