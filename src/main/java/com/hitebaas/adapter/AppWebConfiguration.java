package com.hitebaas.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hitebaas.interceptor.LoginInterceptor;


@Component
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**");

		registry.addInterceptor(loginInterceptor).addPathPatterns("/server/**");
		super.addInterceptors(registry);
	}
	
	
}
