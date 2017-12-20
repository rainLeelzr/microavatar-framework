package microavatar.framework.base.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.base.dao.ParamDao;
import microavatar.framework.base.entity.Param;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParamServiceTest extends BaseTransactionalServiceTestClass<Param, ParamDao, ParamService> {

    @Resource(name = "paramService")
    private ParamService paramService;

    @Override
    public ParamService getService() {
        return paramService;
    }

    @Override
    public Param genEntity() {
        Param entity = new Param();
        entity.setName(RandomStringUtils.randomNumeric(5));
        entity.setType((byte) RandomUtils.nextInt(1, 5));
        entity.setKeyStr(RandomStringUtils.randomNumeric(5));
        entity.setValueStr(RandomStringUtils.randomNumeric(5));
        entity.setValueType((byte) RandomUtils.nextInt(1, 5));
        entity.setRemark(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}