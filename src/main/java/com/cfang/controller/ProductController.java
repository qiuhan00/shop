package com.cfang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfang.dto.IndexProductTree;
import com.cfang.entity.ProductEntity;
import com.cfang.service.CataLogService;
import com.cfang.service.ProductService;

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
	public String proInfo(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		ProductEntity entity = productService.selectById(id);
		model.addAttribute("product", entity);
		List<IndexProductTree> trees = cataLogService.selectIndexProduct();
		model.addAttribute("catalogs", trees);
		List<ProductEntity> hotProducts = productService.selectIndexNav(2);
		model.addAttribute("hotProducts", hotProducts);
		model.addAttribute("user", request.getSession().getAttribute("user"));
		return "user/proinfo";
	}
	
}
