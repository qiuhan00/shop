package com.cfang.service.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfang.entity.City;
import com.cfang.entity.County;
import com.cfang.entity.Province;
import com.cfang.entity.Town;
import com.cfang.mapper.CityMapper;
import com.cfang.mapper.CountyMapper;
import com.cfang.mapper.ProvinceMapper;
import com.cfang.mapper.TownMapper;
import com.cfang.service.MapAreaService;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
@Service
@Slf4j
public class MapAreaServiceImpl implements MapAreaService{
	
	private static String ADCODE_STR = "adcode";
	private static String NAME_STR = "name";
	private static String CITYCODE_STR = "adcode";
	
	@Value("${amap.area.url}")
	private String url;
	@Autowired
	private ProvinceMapper provinceMapper;
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private CountyMapper countyMapper;
	@Autowired
	private TownMapper townMapper;
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public void updateMapArea() {
		// province
		String json = restTemplate.getForObject(url, String.class);
		JSONObject object =JSONObject.parseObject(json);
		if("OK".equals(object.get("info")) && "1".equals(object.get("status"))) {
			JSONObject country = object.getJSONArray("districts").getJSONObject(0);
			JSONArray arrays = country.getJSONArray("districts");
			for(int i = 0; i < arrays.size(); i++) {
				JSONObject province = arrays.getJSONObject(i);
				insertProvince(province);
				//city
				JSONArray arrays2 = remote(province);
				for(int j = 0; j < arrays2.size(); j++) {
					JSONObject city = arrays2.getJSONObject(j);
					insertCity(city, province.getString(ADCODE_STR));
					//county
					JSONArray arrays3 = remote(city);
					for(int m = 0; m < arrays3.size(); m++) {
						JSONObject county = arrays3.getJSONObject(m);
						insertCounty(county, city.getString(ADCODE_STR));
						//town
						JSONArray arrays4 = remote(county);
						for(int n = 0; n < arrays4.size(); n++) {
							JSONObject town = arrays4.getJSONObject(n);
							insertTown(town, county.getString(ADCODE_STR));
						}
					}
				}
			}
		}else {
			log.error("获取高德行政区域数据异常，稍候重试...");
		}
	}
	
	private JSONArray remote(JSONObject obj) {
		String json = restTemplate.getForObject(url + obj.getString(NAME_STR), String.class);
		JSONObject object =JSONObject.parseObject(json);
		JSONObject one = object.getJSONArray("districts").getJSONObject(0);
		JSONArray arrays = one.getJSONArray("districts");
		return arrays;
	}


	public void insertProvince(JSONObject object) {
		Province province = new Province();
		province.setAdCode(object.getString(ADCODE_STR));
		province.setName(object.getString(NAME_STR));
		provinceMapper.insert(province);
	}

	public void insertCity(JSONObject object, String provinceCode) {
		City city = new City();
		city.setAdCode(object.getString(ADCODE_STR));
		city.setName(object.getString(NAME_STR));
		city.setCityCode(object.getString(CITYCODE_STR));
		city.setProvinceCode(provinceCode);
		cityMapper.insert(city);
	}

	public void insertCounty(JSONObject object, String cityCode) {
		County county = new County();
		county.setAdCode(object.getString(ADCODE_STR));
		county.setName(object.getString(NAME_STR));
		county.setCityCode(object.getString(CITYCODE_STR));
		county.setCityCode(cityCode);
		countyMapper.insert(county);
	}

	public void insertTown(JSONObject object, String countyCode) {
		Town town = new Town();
		town.setAdCode(object.getString(ADCODE_STR));
		town.setName(object.getString(NAME_STR));
		town.setCountyCode(countyCode);
		townMapper.insert(town);
	}
}
