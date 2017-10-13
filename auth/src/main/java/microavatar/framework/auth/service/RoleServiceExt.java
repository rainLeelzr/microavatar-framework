/***********************************************************************
 * @模块: 角色业务逻辑实现
 * @模块说明: 角色模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.entity.Role;
import microavatar.framework.common.BaseEntity;
import microavatar.framework.core.database.SqlCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceExt extends RoleService {

    public List<Role> getByUserId(String userId) {
        return getDao().findPage(new SqlCondition().where(BaseEntity.ID, userId).build());
    }
}