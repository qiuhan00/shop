package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
@Data
@Table(name = "tbl_city")
public class City extends BaseEntity{

	private String name;
	private String cityCode;
	private String adCode;
	private String provinceCode;
}
