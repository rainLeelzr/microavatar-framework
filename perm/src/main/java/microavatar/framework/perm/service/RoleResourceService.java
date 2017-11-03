/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.RoleResource;
import microavatar.framework.perm.dao.RoleResourceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleResourceService extends BaseService<RoleResource, RoleResourceDao> {

    @Resource
    private RoleResourceDao roleResourceDao;

    @Override
    protected RoleResourceDao getDao() {
        return roleResourceDao;
    }

}