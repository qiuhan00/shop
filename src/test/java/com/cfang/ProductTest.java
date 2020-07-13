package com.cfang;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cfang.entity.ProductEntity;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.CataLogService;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public class ProductTest extends CommonTest{
	
	@Autowired
	ProductMapper productMapper;

	@Test
	public void insert() {
		ProductEntity entity = new ProductEntity();
		entity.setIntroduce("妙洁 一次性纸杯 8盎司228ml 100只/袋 20袋/箱");
		entity.setProductName("笔记本");
		entity.setPrice(new BigDecimal(19.8));
		entity.setNowPrice(new BigDecimal(19.8));
		entity.setProductHtml("乐购超市旁，未来地铁14号线沿线地段，规划中上海四大商业附中心—真如商业副中心，婚房精装电梯两房。让您一步到位<br />很荣幸栗见能借此机会给您问好，为您介绍此房.<br />超稀缺地铁双南两房，全明户型，满五唯一，业主急需资金周转特急卖。<br />Information of Vernal Garden 满庭芳花园 <br />Location:<br />Located close to Gubei Carrefour and the Hongqiao Airport, 15 mins drive to international schools and downtown area.<br />Description:<br />It offers a full range of accommodations including freestanding houses, semi-detached houses and condominiums. The units are very spacious, well decorated and fully furnished. Resident can enjoy a complete range of onsite <br />recreational facilities. <br />Facilities:<br />Indoor swimming door, gym, sauna, spa, squash, cafe, childrens playground, restaurant, convenience store, snooker, basketball court. <br />Tv Channel:<br />HBO，TVB，BBC，DISCOVERY，CINEMAX，");
		entity.setUnit("袋");
		entity.setStock(10);
		entity.setTitle("笔记本");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		for(int i = 0; i < 4; i++) {
			productMapper.insert(entity);
		}
	}
	
	@Test
	public void selectById() {
		System.out.println(JSON.toJSONString(productMapper.selectByPrimaryKey(10)));
	}
}
