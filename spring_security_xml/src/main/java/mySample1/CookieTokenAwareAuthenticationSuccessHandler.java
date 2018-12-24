package mySample1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CookieTokenAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Encryptor.
     */
    private BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException,
            IOException {
     
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Cookie username = new Cookie(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, bpe.encode(user.getUsername()));
        username.setPath("/");
        Cookie password = new Cookie(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, bpe.encode(user.getPassword()));
        password.setPath("/");

        response.addCookie(username);
        response.addCookie(password);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
