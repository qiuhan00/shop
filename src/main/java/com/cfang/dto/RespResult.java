package com.cfang.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RespResult {

	@NonNull
	private Integer code;
	@NonNull
	private String msg;
	private Object data;
	
	public static RespResult success() {
		return new RespResult(200, "SUCCESS");
	}
	
	public static RespResult success(Object object) {
		RespResult respResult = success();
		respResult.setData(object);
		return respResult;
	}
	
	public static RespResult fail(Integer code, String msg) {
		return new RespResult(code, msg);
	}
	
	public static RespResult fail(Integer code, String msg, Object object) {
		RespResult respResult = fail(code, msg);
		respResult.setData(object);
		return respResult;
	}
}
