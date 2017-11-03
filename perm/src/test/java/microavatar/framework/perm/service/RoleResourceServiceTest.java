package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.entity.RoleResource;
import microavatar.framework.perm.dao.RoleResourceDao;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleResourceServiceTest extends BaseServiceTestClass<RoleResource, RoleResourceDao, RoleResourceService> {

    @Resource(name = "roleResourceService")
    private RoleResourceService roleResourceService;

    @Override
    protected RoleResourceService getService() {
        return roleResourceService;
    }

    @Override
    public RoleResource genEntity() {
        RoleResource entity = new RoleResource();
        entity.setRoleId(PkGenerator.getPk());
        entity.setResourceId(PkGenerator.getPk());
        return entity;
    }

}