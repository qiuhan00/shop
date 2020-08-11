package com.cfang;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.cfang.service.MapAreaService;
import com.thebeastshop.forest.springboot.annotation.ForestScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
@MapperScan(basePackages = "com.cfang.mapper")
@EnableCaching
@EnableAsync
@ServletComponentScan(basePackages = "com.cfang.filter")
@ForestScan(basePackages = "com.cfang.httpInterface")
public class ShopApplication{

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
	
    @Bean
    RestTemplate iniRestTemplate() {
    	RestTemplate restTemplate = new RestTemplate();
    	List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
    	HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("utf-8"));
		converterList.add(0, converter);
		restTemplate.setMessageConverters(converterList);
    	return restTemplate;
    }

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
//		super.addResourceHandlers(registry);
//	}
}
