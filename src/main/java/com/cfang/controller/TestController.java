package com.cfang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {

	@GetMapping("t1")
	public String t1(Model model) {
		return "index";
	}
	
	@GetMapping("t2")
	public String t2(Model model) {
		return "/user/reg";
	}
}
