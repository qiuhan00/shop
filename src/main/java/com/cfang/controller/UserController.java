package com.cfang.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
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

import com.cfang.common.ApiIdempotent;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserLoginDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.entity.UserEntity;
import com.cfang.service.RedisService;
import com.cfang.service.TokenService;
import com.cfang.service.UserService;
import com.cfang.utils.FlushUtil;
import com.cfang.utils.RandomValidateCodeUtil;

import cn.hutool.system.UserInfo;

/**
 * describe：
 * @author cfang 2020-7-10
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	TokenService tokenService;

	@GetMapping("toRedirect/{toView}")
	public String toRegister(@PathVariable("toView") String toView, String toUrl, Model model) throws Exception {
		if(StringUtils.isNotBlank(toUrl)) {
			model.addAttribute("toURL", URLEncoder.encode(toUrl, "utf-8"));
			model.addAttribute("token", tokenService.createToken());
		}
		return "user/" + toView;
	}
	
	@GetMapping("getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		 try {
			//设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            //生成验证码图片方法
            Object[] objects = RandomValidateCodeUtil.getRandcode();
            if(objects.length == 2) {
            	String verifyCode = (String) objects[0];
            	BufferedImage image = (BufferedImage) objects[1];
            	request.getSession().removeAttribute("verifyCode");
            	request.getSession().setAttribute("verifyCode", verifyCode);
            	ImageIO.write(image, "JPEG", response.getOutputStream());
            }else {
				throw new Exception("验证码生成异常，请稍候重试.");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@PostMapping("checkVerify")
    public void checkVerify(String code, HttpServletRequest request, HttpServletResponse response) {
		String result = "success";
        try {
            String random = (String) request.getSession().getAttribute("verifyCode");
            if (random == null || !random.equals(code)) {
            	result = "fail";
            }
            FlushUtil.flushJsonByObject(result, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@PostMapping("register")
	public void register(UserRegisterDto dto, HttpServletResponse response) {
		try {
			int i = userService.regUser(dto);
			FlushUtil.flushJsonByObject("success", response);
		} catch (Exception e) {
			e.printStackTrace();
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
		UserInfoDto entity = userService.loginUser(dto);
		if(null != entity) {
			result = true;
		}
		FlushUtil.success(result, response);
	}
		
	@ApiIdempotent
	@PostMapping("userLogin")
	public void login(UserLoginDto dto, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserInfoDto user = userService.loginUser(dto);
		if(null != user) {
			request.getSession().setAttribute("user", user);
		}
		String toURL = "";
		if(StringUtils.isNotBlank(dto.getToUrl())) {
			toURL = URLDecoder.decode(dto.getToUrl(), "utf-8");
		}
		FlushUtil.success(toURL, response);
	}
	
	@PostMapping("logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		request.getSession().invalidate();
		request.getSession().removeAttribute("user");
		FlushUtil.flushJsonByObject("success", response);
	}
	
}
