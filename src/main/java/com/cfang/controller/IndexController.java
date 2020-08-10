package com.cfang.controller;

import java.io.IOException;
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
import com.cfang.config.ApiLog;
import com.cfang.dto.IndexProductTree;
import com.cfang.dto.UserInfoDto;
import com.cfang.entity.ProductEntity;
import com.cfang.entity.UserEntity;
import com.cfang.service.CataLogService;
import com.cfang.service.ProductService;
import com.cfang.utils.FlushUtil;

@Controller
@RequestMapping("")
public class IndexController {
	
	@Autowired
	private CataLogService cataLogService;
	@Autowired
	private ProductService productService;
	
	@ApiLog(operaterType = "index", operaterModule = "首页模块")
	@GetMapping(value = {"/shop", ""})
	public String index(Model model, HttpServletRequest request, UserInfoDto user) {
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		//滚动商品
		List<ProductEntity> navProducts = productService.selectIndexNav(0);
		model.addAttribute("navProducts", navProducts);
		//推荐产品
		List<ProductEntity> proProducts = productService.selectIndexNav(1);
		model.addAttribute("proProducts", proProducts);
		List<ProductEntity> recProducts = productService.selectIndexNav(3);
		model.addAttribute("recProducts", recProducts);
		model.addAttribute("user", user);
		model.addAttribute("optflag", 0);
		return "index";
	}
	
	@PostMapping("getProducts")
	public void getProducts(Integer cataLogId, HttpServletResponse response) throws Exception {
		try {
			List<ProductEntity> list = productService.selectByCataLogId(cataLogId);
			FlushUtil.flushJsonByObject(list, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping("searchHotProduct")
	public String searchHotProduct(Model model) {
		List<ProductEntity> hotProducts = productService.selectIndexNav(2);
		model.addAttribute("hotProducts", hotProducts);
		return "common::hot_product";
	}
	
	@GetMapping("initCatalogTree")
	public String initCatalogTree(Model model) {
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		return "common::catalog_tree";
	}
	/**
	 * a 有个css样式未搞定，待后续优化调整
	 */
	@GetMapping("initCatalogTree2")
	public String initCatalogTree2(Model model) {
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		return "common::catalog_tree2";
	}
}
