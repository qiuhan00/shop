package com.cfang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.dto.VipUserDto;
import com.cfang.entity.City;
import com.cfang.entity.Province;
import com.cfang.entity.UserEntity;
import com.cfang.service.MapAreaService;
import com.cfang.service.UserService;
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
	@Autowired
	private UserService userService;
	
	@GetMapping("toVip")
	public String toVip(UserInfoDto userEntity, Model model) {
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
	
	@PostMapping("updateUser")
	public void updateUser(VipUserDto dto, UserInfoDto userEntity, HttpServletResponse response, HttpServletRequest request) {
		if(null != dto) {
			dto.setUserId(userEntity.getId());
			dto.setUserCode(userEntity.getUserCode());
			dto.setType("0");
			mapAreaService.updateViper(dto);
			UserInfoDto user = userService.selectUserByUserCode(userEntity.getUserCode());
			if(null != user) {
				request.getSession().removeAttribute("user");
				request.getSession().setAttribute("user", user);
			}
		}
		FlushUtil.success(true, response);
	}
	
	@PostMapping("updateUserPwd")
	public void updateUserPwd(UserInfoDto userEntity, HttpServletResponse response, UserRegisterDto dto) {
		userService.updateUserPwd(dto);
		FlushUtil.success(true, response);
	}
	
	@GetMapping("toView")
	public String toView(Model model, String viewName) {
		
		return "user/" + viewName;
//		return "vip::" + viewName;
	}
}
