package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.ThirdPartyUserDao;
import microavatar.framework.perm.entity.ThirdPartyUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThirdPartyUserServiceTest extends BaseTransactionalServiceTestClass<ThirdPartyUser, ThirdPartyUserDao, ThirdPartyUserService> {

    @Resource(name = "thirdPartyUserService")
    private ThirdPartyUserService thirdPartyUserService;

    @Override
    public ThirdPartyUserService getService() {
        return thirdPartyUserService;
    }

    @Override
    public ThirdPartyUser genEntity() {
        ThirdPartyUser entity = new ThirdPartyUser();
        entity.setUserId(UuidSequence.get());
        entity.setLoginType((byte) RandomUtils.nextInt(1, 5));
        entity.setAccessTime(RandomUtils.nextLong());
        entity.setUniqueId(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}