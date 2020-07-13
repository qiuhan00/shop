package com.cfang.entity;

import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class BaseEntity {

	@Id
	private Integer id;
	private Date createTime;
	private Date updateTime;
}
