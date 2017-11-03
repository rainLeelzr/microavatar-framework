package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.dao.UserRoleDao;
import microavatar.framework.perm.entity.UserRole;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRoleServiceTest extends BaseServiceTestClass<UserRole, UserRoleDao, UserRoleService> {

    @Resource(name = "userRoleService")
    private UserRoleService userRoleService;

    @Override
    protected UserRoleService getService() {
        return userRoleService;
    }

    @Override
    public UserRole genEntity() {
        UserRole entity = new UserRole();
        entity.setUserId(PkGenerator.getPk());
        entity.setRoleId(PkGenerator.getPk());
        return entity;
    }

}