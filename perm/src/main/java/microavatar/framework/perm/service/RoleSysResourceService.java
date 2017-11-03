/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.RoleSysResource;
import microavatar.framework.perm.dao.RoleSysResourceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleSysResourceService extends BaseService<RoleSysResource, RoleSysResourceDao> {

    @Resource
    private RoleSysResourceDao roleSysResourceDao;

    @Override
    protected RoleSysResourceDao getDao() {
        return roleSysResourceDao;
    }

}