package com.cfang.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cfang.dto.CartListDto;
import com.cfang.dto.UserInfoDto;
import com.cfang.entity.CartEntity;
import com.cfang.entity.ProductEntity;
import com.cfang.entity.UserEntity;
import com.cfang.service.CartService;
import com.cfang.service.ProductService;
import com.cfang.utils.FlushUtil;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	
	@GetMapping("toCart")
	public String toCart(HttpServletRequest request, Model model) {
		return "user/cart";
	}
	
	@PostMapping("addCart")
	public void addCart(CartEntity entity, HttpServletResponse response, UserInfoDto user) {
		entity.setUserId(user.getId());
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		boolean result = cartService.addCart(entity);
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("delCartItem")
	public void delCartItem(CartEntity entity, HttpServletResponse response, UserInfoDto user) {
		entity.setUserId(user.getId());
		boolean result = cartService.delCartItem(entity);
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("clearCart")
	public void clearCart(CartEntity entity, HttpServletResponse response, UserInfoDto user) {
		boolean result = cartService.clearCart(user.getId());
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("selectUserCart")
	public String selectUserCart(UserInfoDto user, Model model){
		List<CartListDto> carts = cartService.selectUserCart(user.getId());
		model.addAttribute("carts", carts);
		return "user/cart::carts_item";
	}
	
	@GetMapping("statUserCart")
	public void statUserCart(UserInfoDto user, HttpServletResponse response) {
		List<CartListDto> carts = cartService.selectUserCart(user.getId());
		int number = 0;
		BigDecimal total = BigDecimal.ZERO;
		for(CartListDto dto : carts) {
			number += dto.getQuantity();
			total = total.add(dto.getPrice().multiply(new BigDecimal(dto.getQuantity())));
		}
		JSONObject object = new JSONObject();
		object.put("number", number);
		object.put("total", total);
		FlushUtil.flushJsonByObject(object, response);
	}
}
