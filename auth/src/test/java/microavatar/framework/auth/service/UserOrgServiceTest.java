package microavatar.framework.auth.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.auth.dao.UserOrgDao;
import microavatar.framework.auth.entity.UserOrg;
import microavatar.framework.core.util.pk.PkGenerator;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserOrgServiceTest extends BaseServiceTestClass<UserOrg, UserOrgDao, UserOrgService> {

    @Resource(name = "userOrgService")
    private UserOrgService userOrgService;

    @Override
    protected UserOrgService getService() {
        return userOrgService;
    }

    @Override
    public UserOrg genEntity() {
        UserOrg entity = new UserOrg();
        entity.setUserId(PkGenerator.getPk());
        entity.setOrgId(PkGenerator.getPk());
        return entity;
    }

}