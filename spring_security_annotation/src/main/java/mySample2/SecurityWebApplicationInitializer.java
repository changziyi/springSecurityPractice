package mySample2;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
//official:	a base class AbstractSecurityWebApplicationInitializer that will ensure the springSecurityFilterChain
//	The SecurityWebApplicationInitializer will do the following things:
//		Automatically register the springSecurityFilterChain Filter for every URL in your application
//		Add a ContextLoaderListener that loads the WebSecurityConfig.
	
	// I think: register my spring security configuration class
	public SecurityWebApplicationInitializer() {
		super(SecurityConfiguration2.class);
	}
	
//	i think: this is for spring MVC to ensure that WebSecurityConfig was loaded in our existing ApplicationInitializer
//	@Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[] { WebSecurityConfig.class };
//    }
}
