package com.cfang.dto;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月31日
 */
@Data
public class SendMessageDto {

	private String PhoneNumberSet;
	private String TemplateID;
	private String SmsSdkAppid;
	private String Sign;
	private String TemplateParamSet;
}
