package com.cfang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfang.dto.CartListDto;
import com.cfang.entity.CartEntity;
import com.cfang.mapper.CartMapper;
import com.cfang.service.CartService;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartMapper cartMapper;

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

}
