package com.cfang.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description：
 * @author cfang 2020年7月13日
 */
@WebFilter(filterName = "loginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter{
	
	private static final String[] excludeUrls = new String[] {"/user/toRedirect/login", "/user/userLogin", "/reg", "/shop"};
	private static final String NO_LOGIN = "您还未登录";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		
		//静态资源
		if(uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")
                || uri.endsWith(".gif") || uri.endsWith(".png")){
            chain.doFilter(servletRequest,servletResponse);
            return;
        }

		if(!isNeedFilter(uri)) {
			chain.doFilter(servletRequest, servletResponse);
		}else {
			if(null != session && null != session.getAttribute("user")) {
				chain.doFilter(servletRequest, servletResponse);
			}else {
				String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    response.getWriter().write(this.NO_LOGIN);
                }else{
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    response.sendRedirect(request.getContextPath()+"/login");
                }
                return;
			}
		}
	}
	
	private boolean isNeedFilter(String uri) {
		boolean result = true;
		for(String str : excludeUrls) {
			if(str.contains(uri)) {
				result = false;
				break;
			}
		}
		return result;
	}

}
