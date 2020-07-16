package com.cfang.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：
 * @author cfang 2020年7月16日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxException {

	private String msg;
	
	
	public static AjaxException errorException(String msg) {
		return new AjaxException(msg);
	}
}
