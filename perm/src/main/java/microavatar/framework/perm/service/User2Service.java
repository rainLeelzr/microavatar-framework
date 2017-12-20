/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.dao.User2Dao;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class User2Service extends BaseService<User2, User2Dao> {

    @Resource
    private User2Dao user2Dao;

    @Override
    protected User2Dao getDao() {
        return user2Dao;
    }

}