package com.cfang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserEntity;
import com.cfang.service.UserService;
import com.cfang.utils.FlushUtil;

/**
 * describe：
 * @author cfang 2020-7-10
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("toRedirect/{toView}")
	public String toRegister(@PathVariable("toView") String toView) {
		return "user/" + toView;
	}
	
	@PostMapping("register")
	public void register(UserRegisterDto dto, HttpServletResponse response) {
		try {
			int i = userService.regUser(dto);
			FlushUtil.flushJsonByObject("success", response);
		} catch (Exception e) {
			e.printStackTrace();
			FlushUtil.flushJsonByObject("fail", response);
		}
	}
	
	@PostMapping("validUserName")
	public void validUserName(String userName, HttpServletResponse response) {
		boolean result = false;
		if(StringUtils.isBlank(userName)) {
			result = true;
		}
		UserLoginDto dto = new UserLoginDto();
		dto.setUserName(userName);
		UserEntity entity = userService.loginUser(dto);
		if(null != entity) {
			result = true;
		}
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("userLogin")
	public String login(UserLoginDto dto, Model model, HttpServletRequest request) {
		UserEntity user = userService.loginUser(dto);
		if(null != user) {
			request.getSession().setAttribute("user", user);
		}
		return "index";
	}
	
	@PostMapping("logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
//		request.getSession().removeAttribute("user");
		FlushUtil.flushJsonByObject("success", response);
	}
	
}
