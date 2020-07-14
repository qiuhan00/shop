package com.cfang.controller;

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

import com.cfang.dto.CartListDto;
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
		//热搜产品
		List<ProductEntity> hotProducts = productService.selectIndexNav(2);
		model.addAttribute("hotProducts", hotProducts);
		return "user/cart";
	}
	
	@PostMapping("addCart")
	public void addCart(CartEntity entity, HttpServletResponse response, HttpServletRequest request) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		entity.setUserId(userEntity.getId());
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		boolean result = cartService.addCart(entity);
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("delCartItem")
	public void delCartItem(CartEntity entity, HttpServletResponse response, HttpServletRequest request) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		entity.setUserId(userEntity.getId());
		boolean result = cartService.delCartItem(entity);
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("clearCart")
	public void clearCart(CartEntity entity, HttpServletResponse response, HttpServletRequest request) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		boolean result = cartService.clearCart(userEntity.getId());
		FlushUtil.flushJsonByObject(result, response);
	}
	
	@PostMapping("selectUserCart")
	public String selectUserCart(HttpServletRequest request, Model model){
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		List<CartListDto> carts = cartService.selectUserCart(userEntity.getId());
		model.addAttribute("carts", carts);
		return "user/cart::carts_item";
	}
}
