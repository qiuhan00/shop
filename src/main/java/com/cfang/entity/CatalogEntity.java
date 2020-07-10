package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * describe：
 * @author cfang 2020-7-8
 */
@Data
@Table(name = "tbl_catalog")
public class CatalogEntity extends BaseEntity{

	private String name;
	private String code;
	private String type;
	private int order1;
	private String showInNav;
	private Integer parentId;
	private Integer nodeLevel;
}
