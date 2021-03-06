package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "tbl_user")
public class UserEntity extends BaseEntity{

	private String userCode;
	private String userName;
	private String password;
	private String nickName;
	private String trueName;
	private String phone;
	private String cardNo;
	private String email;
	private String grade;
	
}
