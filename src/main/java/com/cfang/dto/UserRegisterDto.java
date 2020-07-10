package com.cfang.dto;

import lombok.Data;

/**
 * describe：
 * @author cfang 2020-7-10
 */
@Data
public class UserRegisterDto {

	private String userName;
	private String password;
	private String rePassword;
	private String verifyCode;
	
}
