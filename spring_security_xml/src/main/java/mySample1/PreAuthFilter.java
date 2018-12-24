package mySample1;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
@Component
public class PreAuthFilter extends AbstractPreAuthenticatedProcessingFilter  {
	private StringEncryptor encryptor = new StandardPBEStringEncryptor();
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (getPreAuthenticatedPrincipal((HttpServletRequest) request) == null) {
            SecurityContextHolder.clearContext();
        }
        super.doFilter(request, response, chain);
    }
	/**
	 * i think: 之後會讓 AbstractPreAuthenticatedProcessingFilter
	 * 用來產生principle，再拿去產生token，再做驗證， Object principal =
	 * getPreAuthenticatedPrincipal(request);
	 * PreAuthenticatedAuthenticationToken authRequest = new
	 * PreAuthenticatedAuthenticationToken(principal, credentials);
	 * authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	 * authResult = authenticationManager.authenticate(authRequest);
	 * 
	 * 用PreAuthenticatedAuthenticationToken建構子產生token public
	 * PreAuthenticatedAuthenticationToken(Object aPrincipal, Object
	 * aCredentials) { super(null); this.principal = aPrincipal;
	 * this.credentials = aCredentials; }
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		// i think:用base64的requestHeader當token的材料
		// String authHeader = request.getHeader("Authorization");
		// System.out.println("auth encoded in base64 is " +
		// getFromBASE64(authHeader));
		// String authHeader = request.getParameter("userName");
		// if (StringUtils.isNotBlank(authHeader)) {
		// //
		// 可以通過request獲取當前認證過的用户名，比如通過參數、HTTP請求頭或者Cookie獲取token，再通過token調用第三方接口獲取用户名
		// return authHeader;
		// } else {
		// // 如果認證失敗，可以拋出異常
		// throw new PreAuthenticatedCredentialsNotFoundException("failure in
		// getPreAuthenticatedPrincipal");
		// }
		Cookie passwordCookie = WebUtils.getCookie(request, UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
		if (passwordCookie != null) { return encryptor.decrypt(passwordCookie.getValue()); }
		return null;
	}

	private String getFromBASE64(String s) {
		if (s == null) return null;
		Base64.Decoder decoder = Base64.getDecoder();
		try {
			byte[] b = decoder.decode(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 這個方法一般情況下不需要重寫，直接返回空字符串即可 Credentials aren't usually applicable, but if a
	 * credentialsRequestHeader is set, this will be read and used as the
	 * credentials value.
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		Cookie passwordCookie = WebUtils.getCookie(request, UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
		if (passwordCookie != null) { return encryptor.decrypt(passwordCookie.getValue()); }
		return null;
	}
}
