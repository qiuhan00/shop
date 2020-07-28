package com.cfang;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.service.CataLogService;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public class CataLogTest extends BaseTest{
	
	@Autowired
	CataLogService cataLogService;

	@Test
	public void selectIndexProduct() {
		System.out.println(JSON.toJSONString(cataLogService.selectIndexProduct()));
	}
}
