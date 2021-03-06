package com.cfang.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.common.ShopConstants;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.UserRegisterDto;
import com.cfang.dto.VipUserDto;
import com.cfang.dto.resp.VipOrderResp;
import com.cfang.entity.MessageEntity;
import com.cfang.entity.Province;
import com.cfang.entity.UserAddressEntity;
import com.cfang.service.MapAreaService;
import com.cfang.service.MessageService;
import com.cfang.service.OrderService;
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
	@Autowired
	private MessageService messageService;
	@Autowired
	OrderService orderService;
	
	@GetMapping("toVip")
	public String toVip(UserInfoDto userEntity, Model model) {
		model.addAttribute("user", userEntity);
		model.addAttribute("optflag", 2);
		return "user/vipCommon";
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
			userService.updateViper(dto);
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
		dto.setUserCode(userEntity.getUserCode());
		int result = userService.updateUserPwd(dto);
		if(2 == result) {
			FlushUtil.success("原密码错误", response);
		}else {
			FlushUtil.success(true, response);
		}
	}
	
	@GetMapping("toView")
	public String toView(Model model, String viewName, UserInfoDto userEntity) {
		model.addAttribute("user", userEntity);
		if("vipContRight".equals(viewName) || "vipAddressRight".equals(viewName)) {
			List<Province> provinces = mapAreaService.getProvinces();
			model.addAttribute("provinces", provinces);
			if("vipAddressRight".equals(viewName)) {
				List<UserAddressEntity> address = userService.selectByUserCode(userEntity.getUserCode());
				model.addAttribute("address", address.stream().filter(item -> item.getType().equals("1")).collect(Collectors.toList()));
			}
		}
		if("vipOrderRight".equals(viewName)) {
			List<VipOrderResp> orders = orderService.selectUserOrder(userEntity.getUserCode());
			model.addAttribute("orders", orders);
			Map<String, Long> collect = orders.stream().filter(it -> !ShopConstants.orderStatus.C.name().equals(it.getStatus()))
					.collect(Collectors.groupingBy(VipOrderResp::getPayStatus, Collectors.counting()));
			model.addAttribute("paid", null == collect.get("y") ? 0 : collect.get("y"));
			model.addAttribute("unpaid", null == collect.get("n") ? 0 : collect.get("n"));
			model.addAttribute("cancle", orders.stream().filter(it -> ShopConstants.orderStatus.C.name().equals(it.getStatus())).collect(Collectors.toList()).size());
			
		}
		return "user/vip::" + viewName;
	}
	
	@PostMapping("saveOrUpdateAddress")
	public void saveOrUpdateAddress(HttpServletResponse response, UserInfoDto userinfo, UserAddressEntity userAddress) {
		userAddress.setUserCode(userinfo.getUserCode());
		userService.saveOrUpdateAddress(userAddress);
		FlushUtil.success(true, response);
	}
	
	@PostMapping("delAddress")
	public void delAddress(HttpServletResponse response, UserInfoDto userinfo, UserAddressEntity userAddress) {
		userAddress.setUserCode(userinfo.getUserCode());
		userService.delAddress(userAddress);
		FlushUtil.success(true, response);
	}
	
	@PostMapping("liveMessage")
	public void liveMessage(UserInfoDto user, MessageEntity messageEntity, HttpServletResponse response) {
		messageEntity.setUserCode(user.getUserCode());
		messageEntity.setUserName(user.getUserName());
		int result = messageService.insert(messageEntity);
		FlushUtil.success(result, response);
	}
	
	@GetMapping("sendMessage")
	public void sendMessage(UserInfoDto user, String phone, HttpServletResponse response) {
		
	}
}
