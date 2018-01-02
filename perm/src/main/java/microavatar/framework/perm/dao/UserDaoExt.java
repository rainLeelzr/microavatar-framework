/***********************************************************************
 * @模块: 用户
 * @模块说明: 用户模块数据库操作
 ***********************************************************************/

package microavatar.framework.perm.dao;

import microavatar.framework.perm.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDaoExt {

    User getByAccount(String account);

}