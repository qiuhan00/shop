package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
@Data
@Table(name = "tb_town")
public class Town extends BaseEntity{

	private String name;
	private String adCode;
	private String countyCode;
}
