package mySample1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CookieTokenClearingLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Cookie username = new Cookie(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, null);
        username.setPath("/");
        username.setMaxAge(0);
        Cookie password = new Cookie(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, null);
        password.setPath("/");
        password.setMaxAge(0);

        response.addCookie(username);
        response.addCookie(password);

        super.onLogoutSuccess(request, response, authentication);
    }

}
