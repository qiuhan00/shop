package com.cfang.dto;

import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class UserLoginDto {

	@NotNull
	private String userName;
	@NotNull
	private String password;
}
