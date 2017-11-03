/***********************************************************************
 * @模块: 第三方系统用户业务逻辑实现
 * @模块说明: 第三方系统用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.ThirdPartyUser;
import microavatar.framework.perm.dao.ThirdPartyUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThirdPartyUserService extends BaseService<ThirdPartyUser, ThirdPartyUserDao> {

    @Resource
    private ThirdPartyUserDao thirdPartyUserDao;

    @Override
    protected ThirdPartyUserDao getDao() {
        return thirdPartyUserDao;
    }

}