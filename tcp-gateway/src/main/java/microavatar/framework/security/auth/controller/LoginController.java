package microavatar.framework.security.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller()
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/login/error")
    public String loginError() {
        System.out.println("登录失败");
        return "auth/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "auth/logout";
    }

    @GetMapping("test")
    public String test() {
        return "auth/test";
    }

}
