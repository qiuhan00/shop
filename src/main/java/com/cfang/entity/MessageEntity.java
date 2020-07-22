package com.cfang.entity;

import javax.persistence.Table;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年7月22日
 */
@Data
@Table(name = "tbl_message")
public class MessageEntity extends BaseEntity{

	private String message;
	private String phone;
	private String userCode;
	private String userName;
	private String replyMessage;
	private String status; //0-初始化、1-已处理
}
