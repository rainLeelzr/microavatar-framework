package microavatar.framework.auth.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.auth.dao.RoleSysResourceDao;
import microavatar.framework.auth.entity.RoleSysResource;
import microavatar.framework.core.util.pk.PkGenerator;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleSysResourceServiceTest extends BaseServiceTestClass<RoleSysResource, RoleSysResourceDao, RoleSysResourceService> {

    @Resource(name = "roleSysResourceService")
    private RoleSysResourceService roleSysResourceService;

    @Override
    protected RoleSysResourceService getService() {
        return roleSysResourceService;
    }

    @Override
    public RoleSysResource genEntity() {
        RoleSysResource entity = new RoleSysResource();
        entity.setRoleId(PkGenerator.getPk());
        entity.setSysResourceId(PkGenerator.getPk());
        return entity;
    }

}