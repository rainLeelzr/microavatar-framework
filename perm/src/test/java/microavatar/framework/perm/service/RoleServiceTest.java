package microavatar.framework.perm.service;

import microavatar.framework.BaseServiceTestClass;
import microavatar.framework.core.pk.PkGenerator;
import microavatar.framework.perm.dao.RoleDao;
import microavatar.framework.perm.entity.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceTest extends BaseServiceTestClass<Role, RoleDao, RoleService> {

    @Resource(name = "roleService")
    private RoleService roleService;

    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    public Role genEntity() {
        Role entity = new Role();
        entity.setParentId(PkGenerator.getPk());
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setCode(RandomStringUtils.randomNumeric(5));
        entity.setOrderNum((byte) RandomUtils.nextInt(1, 5));
        entity.setCreatedUserId(PkGenerator.getPk());
        entity.setEnabled((byte) RandomUtils.nextInt(1, 5));
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}