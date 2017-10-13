/***********************************************************************
 * @模块: 第三方系统用户业务逻辑实现
 * @模块说明: 第三方系统用户模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.ThirdPartyUserDao;
import microavatar.framework.auth.entity.ThirdPartyUser;
import microavatar.framework.common.BaseService;
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