package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.entity.Org;
import microavatar.framework.perm.dao.OrgDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrgServiceTest extends BaseServiceTestClass<Org, OrgDao, OrgService> {

    @Resource(name = "orgService")
    private OrgService orgService;

    @Override
    protected OrgService getService() {
        return orgService;
    }

    @Override
    public Org genEntity() {
        Org entity = new Org();
        entity.setParentId(PkGenerator.getPk());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setAbbreviation(RandomStringUtils.randomNumeric(5));
        entity.setAddress(RandomStringUtils.randomNumeric(5));
        entity.setContact(RandomStringUtils.randomNumeric(5));
        entity.setOrderNum((byte) RandomUtils.nextInt(1, 5));
        entity.setCreatedUserId(PkGenerator.getPk());
        entity.setEnabled((byte) RandomUtils.nextInt(1, 5));
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}