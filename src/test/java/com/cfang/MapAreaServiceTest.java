package com.cfang;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.dto.VipUserDto;
import com.cfang.entity.UserAddressEntity;
import com.cfang.service.MapAreaService;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
public class MapAreaServiceTest extends CommonTest{
	
	@Autowired
	private MapAreaService mapAreaService;

	@Test
	public void updateMap() {
		try {
			mapAreaService.updateMapArea();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectByUserCode() {
		try {
			List<UserAddressEntity> list = mapAreaService.selectByUserCode("10002");
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateViper() {
		try {
			VipUserDto dto = new VipUserDto();
			dto.setUserId(2);
			dto.setUserCode("10002");
			dto.setPostCode("120304");
			dto.setCardNo("1313213232");
			dto.setPhone("13212341234");
			dto.setProvinceCode("310000");
			dto.setProvinceName("上海市");
			dto.setCityCode("310100");
			dto.setCityName("上海城区");
			dto.setCountyCode("310115");
			dto.setCountyName("浦东新区");
			dto.setTownCode("310115");
			dto.setTownName("川沙新镇");
			dto.setAddressDetail("xx小区xx号xxL");
			dto.setType("1");
			mapAreaService.updateViper(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
