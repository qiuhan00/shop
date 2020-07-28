package com.cfang.service;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cfang.utils.RandomValidateCodeUtil;
import com.google.common.collect.Maps;

/**
 * @description：
 * @author cfang 2020年7月28日
 */
@Component
public class SendMessageService {

	@Value("${dx.app.id}")
	private String appId;
	@Value("${dx.app.pwd}")
	private String pwd;
	@Value("${dx.send.url}")
	private String url;
	@Value("${dx.signid}")
	private String signId;
	@Autowired
	private RedisService redisService;
	@Autowired
	RestTemplate restTemplate;
	
	public boolean sendMessage(String phone) {
		String code = getCode(6);
		String content = "您的验证码为： " + code + ",2分钟内有效。如非本人操作，请忽略【ET】";
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("content", content);
		param.add("Account", appId);
		param.add("Pwd", pwd);
		param.add("Mobile", phone);
		param.add("SignId", signId);
		String json = restTemplate.postForObject(url, param, String.class);
		JSONObject object = JSONObject.parseObject(json);
		int retCode = (int) object.get("Code");
		if(0 == retCode) {
			redisService.set(phone, code, 60 * 2);
			return true;
		}
		return false;
	}
	
	private String getCode(int num) {
		StringBuffer ret = new StringBuffer();
		for (int i = 1; i <= num; i++) {
			ret.append(RandomValidateCodeUtil.getRandomString(new Random().nextInt(10)));
		}
		return ret.toString();
	}
	
}
