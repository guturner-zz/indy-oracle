package org.indyoracle.interceptors;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.indyoracle.security.UserManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.stormpath.sdk.account.Account;

/**
 * Adds the Stormpath account object to all pages for use in HTML.
 * 
 * @author Guy
 *
 */
public class InitUserInterceptor extends HandlerInterceptorAdapter {
	
	// Don't add user object to services:
	private final ArrayList<String> SERVICES = new ArrayList<String>() {{
		add("/user/validate");
	}};
	
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, 
			                  final Object handler, ModelAndView modelAndView) throws Exception {
		
		if (!SERVICES.contains(request.getRequestURI())) {
			Account account = UserManager.getCurrentUser(request);
			modelAndView.addObject("user", account);
		}
	}
	
}
