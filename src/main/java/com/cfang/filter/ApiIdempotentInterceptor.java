package com.cfang.filter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cfang.common.ApiIdempotent;
import com.cfang.exception.BusyException;
import com.cfang.service.TokenService;

/**
 * @description 接口幂等性拦截器
 * @author cfang 2020年8月11日
 */
@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor{
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
        	try {
        		return tokenService.checkToken(request);
			} catch (Exception e) {
//				e.printStackTrace();
				throw new BusyException(e.getMessage());
			}
        }
        return true;
	}
	
}
