package org.indyoracle.interceptors;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.indyoracle.security.UserManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.stormpath.sdk.account.Account;

/**
 * This interceptor adds additional protection against unauthorized access.
 * Stormpath should be sufficient, but this is an extra caution.
 * 
 * @author Guy
 *
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	// Collection of pages that should not require authentication:
	private final ArrayList<String> UNSECURED_PAGES = new ArrayList<String>() {{
		add("/");
		add("/error");
		add("/login");
		add("/logout");
		add("/register");
		add("/user/validate");
	}};
	
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		// Prevent splash screen if user logged in:
		Account account = UserManager.getCurrentUser(request);
		if ( request.getRequestURI().equals("/") && 
		     account != null && 
		     request.getParameter("skip") == null ) {
			response.sendRedirect("/?skip=y");
		}
		
		// Avoid a redirect loop on unsecured pages:
		if (!UNSECURED_PAGES.contains(request.getRequestURI())) {
			
			// Only redirect if not logged in:
			if (UserManager.getCurrentUser(request) == null) {
				response.sendRedirect("/login");
				return false;
			}
		}
		
		return true;
	}
	
}
