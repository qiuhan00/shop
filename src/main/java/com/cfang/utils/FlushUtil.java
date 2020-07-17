package com.cfang.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.cfang.dto.RespResult;

import lombok.extern.slf4j.Slf4j;

/**
 * describe：
 * @author cfang 2020-7-10
 */
@Slf4j
public class FlushUtil {
	
	public static void success(Object object, HttpServletResponse response) {
		RespResult respResult = RespResult.success(object);
		flushJsonByObject(respResult, response);
	}
	
	public static void fail(Integer code, String msg, Object object, HttpServletResponse response) {
		RespResult respResult = RespResult.fail(code, msg, object);
		flushJsonByObject(respResult, response);
	}

	public static void flushJsonByObject(Object object, HttpServletResponse response){
		response.setContentType("application/json; charset=utf-8");
		String json = JSON.toJSONString(object);
		try {
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Json串回写失败，原因如下：", e.getMessage());
		}
	}
	
	public static void flushHtmlByString(String str,HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(str);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>html回写失败，原因如下：", e.getMessage());
		}
	}
}
