/***********************************************************************
 * @模块: 用户组织业务逻辑实现
 * @模块说明: 用户组织模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.UserOrg;
import microavatar.framework.perm.dao.UserOrgDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserOrgService extends BaseService<UserOrg, UserOrgDao> {

    @Resource
    private UserOrgDao userOrgDao;

    @Override
    protected UserOrgDao getDao() {
        return userOrgDao;
    }

}