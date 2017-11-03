/***********************************************************************
 * @模块: 登录日志业务逻辑实现
 * @模块说明: 登录日志模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.LoginLog;
import microavatar.framework.perm.dao.LoginLogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class LoginLogService extends BaseService<LoginLog, LoginLogDao> {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    protected LoginLogDao getDao() {
        return loginLogDao;
    }

}