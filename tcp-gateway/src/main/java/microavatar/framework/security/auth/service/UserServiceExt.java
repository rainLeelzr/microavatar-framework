/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.security.auth.service;

import microavatar.framework.perm.entity.User;
import microavatar.framework.security.auth.dao.UserDaoExt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class UserServiceExt extends UserService {

    @Resource
    private UserDaoExt userDaoExt;

    @Override
    @Cacheable("perm:id")
    public User getById(String id) {
        return super.getById(id);
    }


    @Caching(cacheable = {@Cacheable("perm:account")})
    public User getByAccount(String account) {
        return userDaoExt.getByAccount(account);
    }
}