package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.entity.RoleResource;
import microavatar.framework.perm.dao.RoleResourceDao;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleResourceServiceTest extends BaseTransactionalServiceTestClass<RoleResource, RoleResourceDao, RoleResourceService> {

    @Resource(name = "roleResourceService")
    private RoleResourceService roleResourceService;

    @Override
    public RoleResourceService getService() {
        return roleResourceService;
    }

    @Override
    public RoleResource genEntity() {
        RoleResource entity = new RoleResource();
        entity.setRoleId(UuidSequence.get());
        entity.setResourceId(UuidSequence.get());
        return entity;
    }

}