package com.cfang.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description：CurrentUserArgumentResolver生效配置
 * @author cfang 2020年7月16日
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Autowired
	private CurrentUserArgumentResolver currentUserArgumentResolver;
	
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(currentUserArgumentResolver);
    }
}
