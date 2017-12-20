package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.SysResourceDao;
import microavatar.framework.perm.entity.SysResource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysResourceServiceTest extends BaseTransactionalServiceTestClass<SysResource, SysResourceDao, SysResourceService> {

    @Resource(name = "sysResourceService")
    private SysResourceService sysResourceService;

    @Override
    public SysResourceService getService() {
        return sysResourceService;
    }

    @Override
    public SysResource genEntity() {
        SysResource entity = new SysResource();
        entity.setParentId(UuidSequence.get());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setType((byte) RandomUtils.nextInt(1, 5));
        entity.setHost(RandomStringUtils.randomNumeric(5));
        entity.setUrl(RandomStringUtils.randomNumeric(5));
        entity.setOrderNum((byte) RandomUtils.nextInt(1, 5));
        entity.setEnabled((byte) RandomUtils.nextInt(1, 5));
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}