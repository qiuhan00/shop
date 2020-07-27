package com.cfang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.dto.UserInfoDto;

/**
 * @description：
 * @author cfang 2020年7月27日
 */
@Controller
@RequestMapping("help")
public class HelpController {

	@GetMapping("toHelp")
	public String toHelp(UserInfoDto userEntity, Model model) {
		model.addAttribute("user", userEntity);
		model.addAttribute("optflag", 3);
		return "help/help";
	}
	
	@GetMapping("toView")
	public String toView(Model model, String viewName, UserInfoDto userEntity) {
		model.addAttribute("user", userEntity);
		return "help/common::" + viewName;
	}
}
