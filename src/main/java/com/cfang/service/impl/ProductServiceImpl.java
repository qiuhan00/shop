package com.cfang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cfang.entity.ProductEntity;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.ProductService;

import javassist.compiler.ast.NewExpr;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
		case 2:
			entity.setIsHotSearch("y");
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

	@Override
	public ProductEntity selectById(int id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProductEntity> selectByName(String productName) {
		Example example = new Example(ProductEntity.class);
		Criteria criteria = example.createCriteria();
		criteria.andLike("productName", "%" + productName + "%");
		List<ProductEntity> list = productMapper.selectByExample(example);
		return list;
	}

}
