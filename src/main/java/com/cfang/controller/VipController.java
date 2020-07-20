package com.cfang.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.entity.City;
import com.cfang.entity.Province;
import com.cfang.entity.UserEntity;
import com.cfang.service.MapAreaService;
import com.cfang.utils.FlushUtil;
import com.google.common.collect.Lists;

/**
 * @description：
 * @author cfang 2020年7月17日
 */
@Controller
@RequestMapping("vip")
public class VipController {

	@Autowired
	private MapAreaService mapAreaService;
	
	@GetMapping("toVip")
	public String toVip(UserEntity userEntity, Model model) {
		model.addAttribute("user", userEntity);
		List<Province> provinces = mapAreaService.getProvinces();
		model.addAttribute("provinces", provinces);
		return "user/vip";
	}
	
	@GetMapping("getArea")
	public void getProvinces(String type,String code, HttpServletResponse response){
		List result = Lists.newArrayList();
		if("province".equals(type)) {
			result = mapAreaService.getProvinces();
		}else if("city".equals(type)) {
			result = mapAreaService.getCities(code);
		}else if("county".equals(type)) {
			result = mapAreaService.getCounties(code);
		}else if("town".equals(type)){
			result = mapAreaService.getTowns(code);
		}
		FlushUtil.success(result, response);
	}
	
}
