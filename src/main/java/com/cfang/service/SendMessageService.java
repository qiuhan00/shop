package com.cfang.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cfang.dto.SendMessageDto;
import com.cfang.utils.RandomValidateCodeUtil;
import com.google.common.collect.Maps;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

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
	@Value("${dx.secretId}")
	private String secretId;
	@Value("${dx.secretKey}")
	private String secretKey;
	@Value("${dx.region}")
	private String region;
	@Autowired
	private RedisService redisService;
	@Autowired
	RestTemplate restTemplate;
	
	public boolean sendMessageByTx(String phone) {
		try {
			Credential credential = new Credential(secretId, secretKey);
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint(url);
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			SmsClient smsClient = new SmsClient(credential, region, clientProfile);
			SendMessageDto dto = new SendMessageDto();
			dto.setPhoneNumberSet(phone);
			dto.setSign("一方之见");
			dto.setSmsSdkAppid("1400407375");
			dto.setTemplateID("677713");
			String code = (String) redisService.get(phone);
			if(StringUtils.isBlank(code)) {
				code = getCode(6);
			}
			dto.setTemplateParamSet(code);
			SendSmsRequest sendSmsRequest = SendSmsRequest.fromJsonString(JSON.toJSONString(dto), SendSmsRequest.class);
			SendSmsResponse response = smsClient.SendSms(sendSmsRequest);
			System.out.println(SendSmsResponse.toJsonString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public boolean sendMessage(String phone) {
		String code = (String) redisService.get(phone);
		if(StringUtils.isBlank(code)) {
			code = getCode(6);
		}
		String content = "您的验证码为： " + code + ",2分钟内有效。如非本人操作，请忽略";
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
			/*
			 * 验证码存redis并设置有效期，验证时直接redis中取值，能取到则进行比较，取不到则表明验证码已过期，需重新发送
			 */
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
