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
	private Date createTime;
	private Date updateTime;
}
