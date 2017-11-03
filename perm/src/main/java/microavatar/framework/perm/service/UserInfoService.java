/***********************************************************************
 * @模块: 用户信息业务逻辑实现
 * @模块说明: 用户信息模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.UserInfo;
import microavatar.framework.perm.dao.UserInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoService extends BaseService<UserInfo, UserInfoDao> {

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    protected UserInfoDao getDao() {
        return userInfoDao;
    }

}