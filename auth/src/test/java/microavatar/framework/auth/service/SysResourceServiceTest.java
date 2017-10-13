package microavatar.framework.auth.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.auth.dao.SysResourceDao;
import microavatar.framework.auth.entity.SysResource;
import microavatar.framework.core.util.pk.PkGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysResourceServiceTest extends BaseServiceTestClass<SysResource, SysResourceDao, SysResourceService> {

    @Resource(name = "sysResourceService")
    private SysResourceService sysResourceService;

    @Override
    protected SysResourceService getService() {
        return sysResourceService;
    }

    @Override
    public SysResource genEntity() {
        SysResource entity = new SysResource();
        entity.setParentId(PkGenerator.getPk());
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