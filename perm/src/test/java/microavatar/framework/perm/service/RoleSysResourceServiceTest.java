package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.entity.RoleSysResource;
import microavatar.framework.perm.dao.RoleSysResourceDao;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleSysResourceServiceTest extends BaseTransactionalServiceTestClass<RoleSysResource, RoleSysResourceDao, RoleSysResourceService> {

    @Resource(name = "roleSysResourceService")
    private RoleSysResourceService roleSysResourceService;

    @Override
    public RoleSysResourceService getService() {
        return roleSysResourceService;
    }

    @Override
    public RoleSysResource genEntity() {
        RoleSysResource entity = new RoleSysResource();
        entity.setRoleId(UuidSequence.get());
        entity.setSysResourceId(UuidSequence.get());
        return entity;
    }

}