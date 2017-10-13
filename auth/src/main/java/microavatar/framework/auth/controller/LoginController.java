package microavatar.framework.auth.controller;

import microavatar.framework.auth.entity.User;
import microavatar.framework.auth.service.LoginService;
import microavatar.framework.core.util.log.LogUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    public String loginByAccountAndPwd(String account, String pwd) {
        LogUtil.getLogger().debug("收到用户登录请求：用户名：{}，密码：{}", account, pwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account, pwd);
        usernamePasswordToken.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException uae) {
            return "账号错误";
        } catch (IncorrectCredentialsException ice) {
            return "密码错误";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "错误次数过多";
        }

        //验证是否登录成功
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            return "登录成功";
        } else {
            usernamePasswordToken.clear();
            return "登录失败";
        }
    }

    // @RequestMapping(value = "/logout", method = RequestMethod.GET)
    // public String logout() {
    //     // 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
    //     SecurityUtils.getSubject().logout();
    //     return "退出成功";
    // }
}
