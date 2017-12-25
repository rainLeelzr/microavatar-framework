package microavatar.framework.perm;

import microavatar.framework.perm.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        UserInfoServiceTest.class,
        ThirdPartyUserServiceTest.class,
        LoginLogServiceTest.class,
        SysResourceServiceTest.class,
        RoleServiceTest.class,
        RoleSysResourceServiceTest.class,
        UserRoleServiceTest.class,
        OrgServiceTest.class,
        UserOrgServiceTest.class,
        SysPermissionServiceTest.class,
        RoleSysPermissionServiceTest.class
})
public class PermTestSuite {
}