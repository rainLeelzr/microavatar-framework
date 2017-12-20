package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.LoginLogDao;
import microavatar.framework.perm.entity.LoginLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginLogServiceTest extends BaseTransactionalServiceTestClass<LoginLog, LoginLogDao, LoginLogService> {

    @Resource(name = "loginLogService")
    private LoginLogService loginLogService;

    @Override
    public LoginLogService getService() {
        return loginLogService;
    }

    @Override
    public LoginLog genEntity() {
        LoginLog entity = new LoginLog();
        entity.setUserId(UuidSequence.get());
        entity.setLoginType((byte) RandomUtils.nextInt(1, 5));
        entity.setClientIp(RandomStringUtils.randomNumeric(5));
        entity.setLoginTime(RandomUtils.nextLong());
        entity.setLogoutTime(RandomUtils.nextLong());
        entity.setStatus((byte) RandomUtils.nextInt(1, 5));
        return entity;
    }

}