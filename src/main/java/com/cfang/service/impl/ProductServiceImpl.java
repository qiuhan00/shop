package com.cfang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.entity.ProductEntity;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.ProductService;

/**
 * describe：
 * @author cfang 2020-7-9
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<ProductEntity> selectIndexNav(int type) {
		ProductEntity entity = new ProductEntity();
		switch (type) {
		case 0:
			entity.setShowInNav("y");
			break;
		case 1:
			entity.setIsTimePromotion("y");
			break;
		default:
			entity.setIsRecommend("y");
			break;
		}
		return productMapper.select(entity);
	}

	@Override
	public List<ProductEntity> selectByCataLogId(Integer cataLogId) {
		List<ProductEntity> result = productMapper.selectByCataLogId(cataLogId);
		return result;
	}

}
