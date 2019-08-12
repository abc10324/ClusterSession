package com.sam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Configuration
public class ViewConfig {

	@Bean
	public View index() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/index.jsp");
		return view;
	}
	
	@Bean
	public View regist() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/regist.jsp");
		return view;
	}
	
	@Bean
	public View login() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/login.jsp");
		return view;
	}
	
	@Bean
	public View sessionTest() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/sessionTest.jsp");
		return view;
	}
}
