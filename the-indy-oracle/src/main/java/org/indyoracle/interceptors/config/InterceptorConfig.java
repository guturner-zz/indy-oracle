package org.indyoracle.interceptors.config;

import org.indyoracle.interceptors.AuthenticationInterceptor;
import org.indyoracle.interceptors.InitUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new InitUserInterceptor()).addPathPatterns("/**");
	}
	
}
