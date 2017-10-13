/***********************************************************************
 * @模块: 角色业务逻辑实现
 * @模块说明: 角色模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.RoleDao;
import microavatar.framework.auth.entity.Role;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleService extends BaseService<Role, RoleDao> {

    @Resource
    private RoleDao roleDao;

    @Override
    protected RoleDao getDao() {
        return roleDao;
    }

}