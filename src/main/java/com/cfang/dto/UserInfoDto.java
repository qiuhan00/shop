package com.cfang.dto;

import com.cfang.entity.UserAddressEntity;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月21日
 */
@Data
public class UserInfoDto extends UserAddressEntity{

	private String userName;
	private String password;
	private String nickName;
	private String trueName;
	private String phone;
	private String cardNo;
	private String email;
	private String postCode;
	private String grade;
}
