package com.cfang.service;

import java.util.List;

import com.cfang.dto.VipUserDto;
import com.cfang.entity.City;
import com.cfang.entity.County;
import com.cfang.entity.Province;
import com.cfang.entity.Town;
import com.cfang.entity.UserAddressEntity;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
public interface MapAreaService {

	void updateMapArea();
	
	List<Province> getProvinces();
	
	List<City> getCities(String provinceCode);
	
	List<County> getCounties(String cityCode);
	
	List<Town> getTowns(String countyCode);
	
	void updateViper(VipUserDto dto);
	
	List<UserAddressEntity> selectByUserCode(String userCode);
}
