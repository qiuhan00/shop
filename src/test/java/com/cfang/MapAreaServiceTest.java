package com.cfang;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
