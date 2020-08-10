package com.cfang.service;

import java.util.List;

import com.cfang.entity.ProductEntity;

/**
 * describe：
 * @author cfang 2020-7-9
 */
public interface ProductService {
	
	List<ProductEntity> selectIndexNav(int type);
	
	//根据具体二级类别查询产品
	List<ProductEntity> selectByCataLogId(Integer cataLogId);
	//根据以一级类别查询产品
	List<ProductEntity> selectByOneCataLogId(Integer cataLogId);
	
	ProductEntity selectById(int id);
	
	List<ProductEntity> selectByName(String productName);
	
	ProductEntity selectByProductCode(Long code);
}
