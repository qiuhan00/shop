package com.cfang.service;

import java.util.List;

import com.cfang.entity.ProductEntity;

/**
 * describe：
 * @author cfang 2020-7-9
 */
public interface ProductService {
	
	List<ProductEntity> selectIndexNav(int type);
	
	List<ProductEntity> selectByCataLogId(Integer cataLogId);
}
