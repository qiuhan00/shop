package com.cfang.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.cfang.utils.CommonRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.service.RedisService;
import com.cfang.service.TokenService;

import cn.hutool.core.lang.UUID;

/**
 * @description 
 * @author cfang 2020年8月11日
 */
@Service
public class TokenServiceImpl implements TokenService{

	private static final String TOKEN_PREFIX  = "token:";
	private static final String TOKEN_NAME = "token";
	@Autowired
	CommonRedisUtil commonRedisUtil;
	
	@Override
	public String createToken() {
		String token = TOKEN_PREFIX + UUID.fastUUID().toString(true);
		commonRedisUtil.set(token, token, 60);
		return token;
	}

	@Override
	public boolean checkToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_NAME);
		if(StringUtils.isBlank(token)) {
			token = request.getParameter(TOKEN_NAME);
			if(StringUtils.isBlank(token)) {
				throw new RuntimeException("接口未携带token参数");
			}
		}
		if(!commonRedisUtil.hasKey(token)) {
			throw new RuntimeException("重复提交，稍后重试");
		}
		boolean result = commonRedisUtil.del(token);
		if(!result) {
			throw new RuntimeException("重复提交，稍后重试");
		}
		return true;
	}

}
