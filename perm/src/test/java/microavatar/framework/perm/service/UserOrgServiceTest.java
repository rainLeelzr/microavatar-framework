package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.UserOrgDao;
import microavatar.framework.perm.entity.UserOrg;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserOrgServiceTest extends BaseTransactionalServiceTestClass<UserOrg, UserOrgDao, UserOrgService> {

    @Resource(name = "userOrgService")
    private UserOrgService userOrgService;

    @Override
    public UserOrgService getService() {
        return userOrgService;
    }

    @Override
    public UserOrg genEntity() {
        UserOrg entity = new UserOrg();
        entity.setUserId(UuidSequence.get());
        entity.setOrgId(UuidSequence.get());
        return entity;
    }

}