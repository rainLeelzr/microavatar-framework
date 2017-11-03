package microavatar.framework.security.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller()
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String login() {
        return "perm/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "perm/logout";
    }

}
