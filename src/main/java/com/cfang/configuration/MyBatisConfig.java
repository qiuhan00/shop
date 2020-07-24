package com.cfang.configuration;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cfang.filter.CustomInterceptor;
import com.github.pagehelper.PageHelper;

/**
 * @description：
 * @author cfang 2020年7月24日
 */
@Configuration
public class MyBatisConfig {

	@Bean
	public Interceptor initInterceptor() {
		return new CustomInterceptor();
	}
	
	@Bean
	public PageHelper init() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect","mysql");
		properties.setProperty("reasonable","true");	//开启合法校验，pageNum<0 会查询第一页，大于maxPage查询最后一页
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
