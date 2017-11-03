/***********************************************************************
 * @模块: 系统资源业务逻辑实现
 * @模块说明: 系统资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.database.SqlCondition;
import microavatar.framework.core.mvc.BaseEntity;
import microavatar.framework.perm.entity.SysResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysResourceServiceExt extends SysResourceService {

    private RoleResourceServiceExt roleResourceServiceExt;

    public List<SysResource> getByRoleId(String roleId) {
        List<String> sysResourceIds = roleResourceServiceExt.getSysResourceIdsByRoleId(roleId);

        return getDao().findPage(new SqlCondition().where(BaseEntity.IDS, sysResourceIds).build());
    }

}