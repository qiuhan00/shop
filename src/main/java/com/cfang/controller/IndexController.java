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

import com.alibaba.fastjson.JSON;
import com.cfang.dto.IndexProductTree;
import com.cfang.entity.ProductEntity;
import com.cfang.entity.UserEntity;
import com.cfang.service.CataLogService;
import com.cfang.service.ProductService;
import com.cfang.utils.FlushUtil;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private CataLogService cataLogService;
	@Autowired
	private ProductService productService;

	@GetMapping(value = {"/shop",""})
	public String index(Model model, HttpServletRequest request) {
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		//滚动商品
		List<ProductEntity> navProducts = productService.selectIndexNav(0);
		model.addAttribute("navProducts", navProducts);
		//推荐产品
		List<ProductEntity> proProducts = productService.selectIndexNav(1);
		model.addAttribute("proProducts", proProducts);
		//热搜产品
		List<ProductEntity> hotProducts = productService.selectIndexNav(2);
		model.addAttribute("hotProducts", hotProducts);
		//促销产品
		List<ProductEntity> recProducts = productService.selectIndexNav(3);
		model.addAttribute("recProducts", recProducts);
		//获取当前登录用户
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		return "index";
	}
	
	@PostMapping("getProducts")
	public void getProducts(Integer cataLogId, HttpServletResponse response) {
		try {
			List<ProductEntity> list = productService.selectByCataLogId(cataLogId);
			FlushUtil.flushJsonByObject(list, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
