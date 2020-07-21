package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月21日
 */
@Data
@Table(name = "tbl_user_address")
public class UserAddressEntity extends BaseEntity{

	private String userCode;
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
