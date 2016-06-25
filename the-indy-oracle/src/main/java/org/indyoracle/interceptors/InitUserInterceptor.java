package org.indyoracle.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.indyoracle.security.UserManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.stormpath.sdk.account.Account;

public class InitUserInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, 
			                  final Object handler, ModelAndView modelAndView) throws Exception {
		Account account = UserManager.getCurrentUser(request);
		modelAndView.addObject("user", account);
	}
	
}
