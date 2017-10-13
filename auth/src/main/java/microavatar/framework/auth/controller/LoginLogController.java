/***********************************************************************
 * @模块: 登录日志业务逻辑实现
 * @模块说明: 登录日志模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.LoginLogDao;
import microavatar.framework.auth.entity.LoginLog;
import microavatar.framework.auth.service.LoginLogService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/loginLog")
public class LoginLogController extends BaseController<LoginLogService, LoginLogDao, LoginLog> {

    @Resource
    private LoginLogService loginLogService;

    @Override
    protected LoginLogService getService() {
        return loginLogService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public LoginLog getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}