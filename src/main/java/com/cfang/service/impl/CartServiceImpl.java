package com.cfang.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.dto.CartListDto;
import com.cfang.entity.CartEntity;
import com.cfang.entity.ProductEntity;
import com.cfang.mapper.CartMapper;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.CartService;
import com.google.common.collect.Lists;

import cn.hutool.core.bean.BeanUtil;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	ProductMapper productMapper;

	@Override
	public boolean addCart(CartEntity entity) {
		int i = cartMapper.insert(entity);
		return i > 0;
	}

	@Override
	public boolean delCartItem(CartEntity entity) {
		int i = cartMapper.deleteByPrimaryKey(entity.getId());
		return i > 0;
	}

	@Override
	public boolean clearCart(Integer userId) {
		int i = cartMapper.clearCart(userId);
		return i > 0;
	}

	@Override
	public List<CartListDto> selectUserCart(Integer userId) {
		List<CartListDto> result = cartMapper.selectUserCart(userId);
		return result;
	}

	@Override
	public List<CartListDto> selectCartsToOrder(String cartIds) {
		List<CartListDto> ret = Lists.newArrayList();
		List<String> list = Arrays.asList(cartIds.split(","));
		list.forEach(item -> {
			CartEntity cartEntity = cartMapper.selectByPrimaryKey(item);
			CartListDto dto = new CartListDto();
			BeanUtil.copyProperties(cartEntity, dto, false);
			ProductEntity entity = productMapper.selectByProductCode(cartEntity.getProductCode());
			dto.setPicture(entity.getPicture());
			dto.setIntroduce(entity.getIntroduce());
			dto.setPrice(entity.getPrice());
			ret.add(dto);
		});
		return ret;
	}

}
