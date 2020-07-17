package com.cfang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.entity.UserEntity;
import com.cfang.service.MapAreaService;

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
		return "user/vip";
	}
	
	
}
