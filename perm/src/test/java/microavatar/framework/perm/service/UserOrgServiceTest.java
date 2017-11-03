package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.entity.UserOrg;
import microavatar.framework.perm.dao.UserOrgDao;
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