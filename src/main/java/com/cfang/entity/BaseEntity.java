package com.cfang.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

@Data
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	// mybatis拦截器中，获取父类public属性，设置时间值
	public Date createTime;
	public Date updateTime;
}
