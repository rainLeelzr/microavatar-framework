package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.entity.UserInfo;
import microavatar.framework.perm.dao.UserInfoDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserInfoServiceTest extends BaseTransactionalServiceTestClass<UserInfo, UserInfoDao, UserInfoService> {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @Override
    public UserInfoService getService() {
        return userInfoService;
    }

    @Override
    public UserInfo genEntity() {
        UserInfo entity = new UserInfo();
        entity.setUserId(UuidSequence.get());
        entity.setRealName(RandomStringUtils.randomNumeric(5));
        entity.setGender((byte) RandomUtils.nextInt(1, 5));
        entity.setQq(RandomStringUtils.randomNumeric(5));
        entity.setTelephone(RandomStringUtils.randomNumeric(5));
        entity.setMobilePhone(RandomStringUtils.randomNumeric(5));
        entity.setEmail(RandomStringUtils.randomNumeric(5));
        entity.setBirthday(RandomUtils.nextLong());
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}