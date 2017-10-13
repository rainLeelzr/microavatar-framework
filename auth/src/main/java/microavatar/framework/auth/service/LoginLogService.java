/***********************************************************************
 * @模块: 登录日志业务逻辑实现
 * @模块说明: 登录日志模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.LoginLogDao;
import microavatar.framework.auth.entity.LoginLog;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginLogService extends BaseService<LoginLog, LoginLogDao> {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    protected LoginLogDao getDao() {
        return loginLogDao;
    }

}