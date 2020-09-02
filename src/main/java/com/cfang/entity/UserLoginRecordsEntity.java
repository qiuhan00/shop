package com.cfang.entity;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description 记录用户64天内的登录情况
 * @author cfang 2020年9月2日
 */
@Data
@Accessors(chain = true)
@Table(name = "tbl_user_login")
public class UserLoginRecordsEntity extends BaseEntity{
	
	private String userCode;
	private Date loginTs;
	private Long loginBit;
}
