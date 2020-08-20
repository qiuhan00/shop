package com.cfang;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.entity.ProductEntity;
import com.cfang.mapper.CartMapper;
import com.cfang.mapper.OrderMapper;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.CartService;
import com.cfang.service.CataLogService;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public class OrderTest extends BaseTest{
	
	@Autowired
	OrderMapper orderMapper;
	
	@Test
	public void selectUserOrder() {
		try {
			System.out.println(orderMapper.selectUserOrder("10001"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
