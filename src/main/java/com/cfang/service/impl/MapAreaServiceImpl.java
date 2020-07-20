package com.cfang.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
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

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
	@Value("${amap.area.filePath}")
	private String filePath;
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
		try {
			String json = restTemplate.getForObject(url, String.class);
			JSONObject object =JSONObject.parseObject(json);
			if("OK".equals(object.get("info")) && "1".equals(object.get("status"))) {
//				writeFile(object);
				// province
				JSONObject one = object.getJSONArray("districts").getJSONObject(0);
				JSONArray provinces = one.getJSONArray("districts");
				for(int i = 0; i < provinces.size(); i++) {
					JSONObject province = provinces.getJSONObject(i);
					insertProvince(province);
					//city
					JSONArray citys = province.getJSONArray("districts");
					for(int j = 0; j < citys.size(); j++) {
						JSONObject city = citys.getJSONObject(j);
						insertCity(city, province.getString(ADCODE_STR));
						//county
						JSONArray countys = city.getJSONArray("districts");
						for(int m = 0; m < countys.size(); m++) {
							JSONObject county = countys.getJSONObject(m);
							insertCounty(county, city.getString(ADCODE_STR));
							//town
							JSONArray towns = county.getJSONArray("districts");
							for(int n = 0; n < towns.size(); n++) {
								JSONObject town = towns.getJSONObject(n);
								insertTown(town, county.getString(ADCODE_STR));
							}
						}
					}
				}
			}else {
				log.error("获取高德行政区域数据异常，稍候重试...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//序列化写文件
	private void writeFile(Object json) throws Exception {
		FileOutputStream outputStream = new FileOutputStream(new File(filePath));
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(json);
		objectOutputStream.close();
	}
	
	private Object readFile() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		return objectInputStream.readObject();
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

	@Override
	public List<Province> getProvinces() {
		return provinceMapper.selectAll();
	}

	@Override
	public List<City> getCities(String provinceCode) {
		Example example = new Example(City.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("provinceCode", provinceCode);
		return cityMapper.selectByExample(example);
	}

	@Override
	public List<County> getCounties(String cityCode) {
		Example example = new Example(County.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cityCode", cityCode);
		return countyMapper.selectByExample(example);
	}

	@Override
	public List<Town> getTowns(String countyCode) {
		Example example = new Example(Town.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("countyCode", countyCode);
		return townMapper.selectByExample(example);
	}
}
