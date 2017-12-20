package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.RoleSysPermissionDao;
import microavatar.framework.perm.entity.RoleSysPermission;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleSysPermissionServiceTest extends BaseTransactionalServiceTestClass<RoleSysPermission, RoleSysPermissionDao, RoleSysPermissionService> {

    @Resource(name = "roleSysPermissionService")
    private RoleSysPermissionService roleSysPermissionService;

    @Override
    public RoleSysPermissionService getService() {
        return roleSysPermissionService;
    }

    @Override
    public RoleSysPermission genEntity() {
        RoleSysPermission entity = new RoleSysPermission();
        entity.setRoleId(UuidSequence.get());
        entity.setSysPermissionId(UuidSequence.get());
        return entity;
    }

}