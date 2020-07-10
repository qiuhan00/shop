package com.cfang.entity;

import java.util.Date;

import lombok.Data;

@Data
public class BaseEntity {

	private Integer id;
	private Date createTime;
	private Date updateTime;
}
