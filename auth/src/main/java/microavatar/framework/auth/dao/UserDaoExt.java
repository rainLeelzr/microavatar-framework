/***********************************************************************
 * @模块: 用户
 * @模块说明: 用户模块数据库操作
 ***********************************************************************/

package microavatar.framework.auth.dao;

import microavatar.framework.auth.entity.User;

public interface UserDaoExt extends UserDao {

    User getByAccount(String account);

}