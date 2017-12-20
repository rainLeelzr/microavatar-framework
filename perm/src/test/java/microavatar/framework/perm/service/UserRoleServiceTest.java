package microavatar.framework.perm.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.core.support.sequence.impl.UuidSequence;
import microavatar.framework.perm.dao.UserRoleDao;
import microavatar.framework.perm.entity.UserRole;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRoleServiceTest extends BaseTransactionalServiceTestClass<UserRole, UserRoleDao, UserRoleService> {

    @Resource(name = "userRoleService")
    private UserRoleService userRoleService;

    @Override
    public UserRoleService getService() {
        return userRoleService;
    }

    @Override
    public UserRole genEntity() {
        UserRole entity = new UserRole();
        entity.setUserId(UuidSequence.get());
        entity.setRoleId(UuidSequence.get());
        return entity;
    }

}