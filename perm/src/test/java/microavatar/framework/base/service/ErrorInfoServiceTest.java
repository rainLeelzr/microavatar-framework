package microavatar.framework.base.service;

import microavatar.framework.BaseTransactionalServiceTestClass;
import microavatar.framework.base.dao.ErrorInfoDao;
import microavatar.framework.base.entity.ErrorInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ErrorInfoServiceTest extends BaseTransactionalServiceTestClass<ErrorInfo, ErrorInfoDao, ErrorInfoService> {

    @Resource(name = "errorInfoService")
    private ErrorInfoService errorInfoService;

    @Override
    public ErrorInfoService getService() {
        return errorInfoService;
    }

    @Override
    public ErrorInfo genEntity() {
        ErrorInfo entity = new ErrorInfo();
        entity.setKeyStr(RandomStringUtils.randomNumeric(5));
        entity.setCode(RandomUtils.nextInt(1, 1000));
        entity.setMsg(RandomStringUtils.randomNumeric(5));
        return entity;
    }

}