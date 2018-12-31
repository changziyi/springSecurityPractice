package mySample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@EnableGlobalAuthentication
public class SecurityConfiguration2 extends WebSecurityConfigurerAdapter {

	// i think: Autowired global configuration can't be used with override configure
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("benny").password("12345").roles("USER").and().withUser("denny")
				.password("1234").roles("JUMPPY").and();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("1234").roles("USER, ADMIN").and().withUser("benny")
//				.password("1234").roles("USER").and();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// String role = "hasRole('ADMIN') and hasRole('HIRED')";
//		.antMatchers("/oneRole").hasAnyRole("ADMIN",
		// "ONEROLE").anyRequest().authenticated().and()
		// .formLogin();
		// http.formLogin().loginPage("/login").defaultSuccessUrl("/index").failureUrl("/login?error").permitAll().and()
		// .logoutSuccessUrl("/index").permitAll();
		http.authorizeRequests()
//				.anyRequest().authenticated()
				.antMatchers("/login.jsp", "/logout.jsp").access("IS_AUTHENTICATED_ANONYMOUSLY")
				.antMatchers("/hasRoleJumppy.jsp").hasRole("JUMPPY").and().formLogin()
//				.loginPage("/login.jsp")
//				.permitAll()
				.failureUrl("/login.jsp?error").permitAll().and()
//				.defaultSuccessUrl("/logout.jsp").successForwardUrl("/logout.jsp").permitAll()
				.rememberMe().tokenValiditySeconds(0).key("myKey").and().logout().logoutUrl("/logout.jsp")
				.logoutSuccessUrl("/login.jsp").and();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true).ignoring().antMatchers("/resources/**");
	}

}
