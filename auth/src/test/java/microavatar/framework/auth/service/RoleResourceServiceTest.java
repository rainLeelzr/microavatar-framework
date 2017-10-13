package microavatar.framework.auth.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.auth.dao.RoleResourceDao;
import microavatar.framework.auth.entity.RoleResource;
import microavatar.framework.core.util.pk.PkGenerator;

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