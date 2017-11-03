/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.security.auth.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.User;
import microavatar.framework.security.auth.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class UserService extends BaseService<User, UserDao> {

    @Resource
    private UserDao userDao;

    @Override
    protected UserDao getDao() {
        return userDao;
    }

}