package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.SysPermissionDao;
import microavatar.framework.perm.entity.SysPermission;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysPermissionServiceTest extends BaseTransactionalServiceTestClass<SysPermission, SysPermissionDao, SysPermissionService> {

    @Resource(name = "sysPermissionService")
    private SysPermissionService sysPermissionService;

    @Override
    public SysPermissionService getService() {
        return sysPermissionService;
    }

    @Override
    public SysPermission genEntity() {
        SysPermission entity = new SysPermission();
        entity.setSysResourceId(UuidSequence.get());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setCode(RandomStringUtils.randomNumeric(5));
        entity.setOrderNum((byte) RandomUtils.nextInt(1, 5));
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}