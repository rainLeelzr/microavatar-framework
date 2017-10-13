/***********************************************************************
 * @模块: 用户组织业务逻辑实现
 * @模块说明: 用户组织模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.UserOrgDao;
import microavatar.framework.auth.entity.UserOrg;
import microavatar.framework.common.BaseService;
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