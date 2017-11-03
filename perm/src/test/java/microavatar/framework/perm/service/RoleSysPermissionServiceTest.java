package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.dao.RoleSysPermissionDao;
import microavatar.framework.perm.entity.RoleSysPermission;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleSysPermissionServiceTest extends BaseServiceTestClass<RoleSysPermission, RoleSysPermissionDao, RoleSysPermissionService> {

    @Resource(name = "roleSysPermissionService")
    private RoleSysPermissionService roleSysPermissionService;

    @Override
    protected RoleSysPermissionService getService() {
        return roleSysPermissionService;
    }

    @Override
    public RoleSysPermission genEntity() {
        RoleSysPermission entity = new RoleSysPermission();
        entity.setRoleId(PkGenerator.getPk());
        entity.setSysPermissionId(PkGenerator.getPk());
        return entity;
    }

}