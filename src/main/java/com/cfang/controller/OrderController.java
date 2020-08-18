package com.cfang.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cfang.common.ShopConstants;
import com.cfang.dto.CartListDto;
import com.cfang.dto.UserInfoDto;
import com.cfang.dto.req.OrderReq;
import com.cfang.dto.resp.OrderResp;
import com.cfang.entity.CartEntity;
import com.cfang.entity.OrderEntity;
import com.cfang.entity.PayChannelEntity;
import com.cfang.entity.ProductEntity;
import com.cfang.entity.UserAddressEntity;
import com.cfang.entity.UserEntity;
import com.cfang.service.CartService;
import com.cfang.service.OrderService;
import com.cfang.service.ProductService;
import com.cfang.service.UserService;
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
	@Autowired
	private UserService userService;
	@Autowired
	OrderService orderService;
	
	@GetMapping("toCart")
	public String toCart(HttpServletRequest request, Model model) {
		return "user/cart";
	}
	
	@PostMapping("addCart")
	public void addCart(CartEntity entity, HttpServletResponse response, UserInfoDto user) {
		entity.setUserId(user.getId());
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
	
	@GetMapping("toOrder")
	public String toOrder(UserInfoDto user, String cartIds, Model model) {
		List<CartListDto> carts = cartService.selectCartsToOrder(cartIds);
		int number = 0;
		BigDecimal total = BigDecimal.ZERO;
		for(CartListDto dto : carts) {
			number += dto.getQuantity();
			total = total.add(dto.getPrice().multiply(new BigDecimal(dto.getQuantity())));
		}
		model.addAttribute("number", number);
		model.addAttribute("total", total);
		model.addAttribute("carts", carts);
		List<UserAddressEntity> address = userService.selectByUserCode(user.getUserCode());
		model.addAttribute("address", address);
		List<PayChannelEntity> paychannels = orderService.selectAllPays();
		model.addAttribute("paychannels", paychannels);
		return "user/order";
	}
	
	@PostMapping("createOrder")
	public void createOrder(UserInfoDto user, @RequestBody OrderReq req, HttpServletResponse response) {
		OrderEntity entity = orderService.createOrder(user, req);
		String payName = orderService.selectAllPays().stream().filter(item -> req.getPayChannelId() == item.getId()).findFirst().get().getPayName();
		String orderNo = entity.getOrderNo();
		OrderResp resp = new OrderResp().setOrderNo(orderNo).setPayName(payName);
		FlushUtil.success(resp, response);
	}
	
	@GetMapping("success")
	public String success(HttpServletRequest request, Model model) {
		String payName = request.getParameter("payName");
		String orderNo = request.getParameter("orderNo");
		model.addAttribute("payName", payName);
		model.addAttribute("orderNo", orderNo);
		return "user/success";
	}
	
	@GetMapping("pay")
	public void pay(HttpServletResponse response, Model model, String orderNo) {
		OrderEntity entity = new OrderEntity().setOrderNo(orderNo)
				.setStatus(ShopConstants.orderStatus.I.name())
				.setPayStatus(ShopConstants.PRODUCT_Y);
		int result = orderService.updateOrder(entity);
		FlushUtil.success(result, response);
	}
}
