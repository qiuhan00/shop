package com.cfang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.dto.IndexProductTree;
import com.cfang.dto.UserInfoDto;
import com.cfang.entity.ProductEntity;
import com.cfang.entity.UserEntity;
import com.cfang.service.CataLogService;
import com.cfang.service.ProductService;
import com.google.common.collect.Lists;

/**
 * describe：
 * @author cfang 2020年7月13日
 */
@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CataLogService cataLogService;
	
	@GetMapping("proinfo/{id}")
	public String proInfo(@PathVariable("id") Integer id, Model model, UserInfoDto user) {
		ProductEntity entity = productService.selectById(id);
		model.addAttribute("product", entity);
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		List<ProductEntity> hotProducts = productService.selectIndexNav(2);
		model.addAttribute("hotProducts", hotProducts);
		model.addAttribute("user", user);
		return "user/proinfo";
	}
	
	@GetMapping("searchPro/{type}")
	public String searchPro(@PathVariable("type") String type, String searchVal, Model model, UserInfoDto user) {
		List<ProductEntity> list = Lists.newArrayList();
		if("0".equals(type)) { //首页分类产品更多
			list = productService.selectByOneCataLogId(Integer.parseInt(searchVal));
			searchVal = "";
		}else { //根据产品名搜索
			list = productService.selectByName(searchVal);
		}
		model.addAttribute("product", list);
		model.addAttribute("user", user);
		model.addAttribute("pname", searchVal);
		model.addAttribute("optflag", 1);
		return "user/product";
	}
	
}
