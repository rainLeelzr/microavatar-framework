/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.perm.dao.UserDaoExt;
import microavatar.framework.perm.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceExt {

    @Resource
    private UserService userService;

    @Resource
    private UserDaoExt userDaoExt;

    @Cacheable("perm:id")
    public User getById(Long id) {
        return userService.getById(id);
    }


    @Caching(cacheable = {@Cacheable("perm:account")})
    public User getByAccount(String account) {
        return userDaoExt.getByAccount(account);
    }
}