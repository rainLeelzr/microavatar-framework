/***********************************************************************
 * @模块: 错误信息业务逻辑实现
 * @模块说明: 错误信息模块服务
 ***********************************************************************/

package microavatar.framework.base.service;

import microavatar.framework.base.dao.ErrorInfoDao;
import microavatar.framework.base.entity.ErrorInfo;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ErrorInfoService extends BaseService<ErrorInfo, ErrorInfoDao> {

    @Resource
    private ErrorInfoDao errorInfoDao;

    @Override
    protected ErrorInfoDao getDao() {
        return errorInfoDao;
    }

}