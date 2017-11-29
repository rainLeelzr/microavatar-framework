package microavatar.framework.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        System.out.println("LoginFailureHandler");
        exception.printStackTrace();
        response.getWriter().write("{\"hello你好，登录失败\"}");
    }
}
