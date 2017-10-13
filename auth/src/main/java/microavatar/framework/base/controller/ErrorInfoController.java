/***********************************************************************
 * @模块: 错误信息业务逻辑实现
 * @模块说明: 错误信息模块服务
 ***********************************************************************/

package microavatar.framework.base.controller;

import microavatar.framework.base.dao.ErrorInfoDao;
import microavatar.framework.base.entity.ErrorInfo;
import microavatar.framework.base.service.ErrorInfoService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/base/errorInfo")
public class ErrorInfoController extends BaseController<ErrorInfoService, ErrorInfoDao, ErrorInfo> {

    @Resource
    private ErrorInfoService errorInfoService;

    @Override
    protected ErrorInfoService getService() {
        return errorInfoService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public ErrorInfo getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}