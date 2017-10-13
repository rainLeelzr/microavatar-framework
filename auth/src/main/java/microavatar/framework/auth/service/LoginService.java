/***********************************************************************
 * @模块: 登录日志业务逻辑实现
 * @模块说明: 登录日志模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.UserDaoExt;
import microavatar.framework.auth.entity.User;
import microavatar.framework.base.entity.ErrorInfo;
import microavatar.framework.base.service.ErrorInfoServiceExt;
import microavatar.framework.result.Result;
import microavatar.framework.base.service.ResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    @Resource
    private UserDaoExt userDaoExt;

    @Resource
    private ErrorInfoServiceExt errorInfoServiceExt;

    @Resource
    private ResultService resultService;

    /**
     * 根据用户名和密码登录系统
     *
     * @param account 用户名
     * @param pwd     密码
     */
    public Result login(String account, String pwd) {
        User user = userDaoExt.getByAccount(account);
        if (user == null) {
            ErrorInfo errorInfo = errorInfoServiceExt.getByKey("login_account_error");
            return resultService.parseFromErrorInfo(errorInfo);
        }

        if (!user.getPwd().equals(pwd)) {
            ErrorInfo errorInfo = errorInfoServiceExt.getByKey("login_pwd_error");
            return resultService.parseFromErrorInfo(errorInfo);
        }

        return resultService.parseFromSuccessData(user);
    }
}