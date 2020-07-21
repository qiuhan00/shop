package com.cfang.dto;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月21日
 */
@Data
public class VipUserDto {

	private Integer userId;
	private String userCode;
	private String postCode;
	private String cardNo;
	private String phone;
	private String provinceCode;
	private String provinceName;
	private String cityCode;
	private String cityName;
	private String countyCode;
	private String countyName;
	private String townCode;
	private String townName;
	private String addressDetail;
	private String type;
}
