/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.RoleSysResourceDao;
import microavatar.framework.auth.entity.RoleSysResource;
import microavatar.framework.common.BaseService;
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