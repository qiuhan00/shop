package com.cfang.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 
 * @author cfang 2020年8月11日
 */
public interface TokenService {

	String createToken();
	boolean checkToken(HttpServletRequest request);
}
