package com.cfang.service;

import java.util.List;

import com.cfang.dto.CartListDto;
import com.cfang.entity.CartEntity;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
public interface CartService {

	boolean addCart(CartEntity entity);
	
	boolean delCartItem(CartEntity entity);
	
	boolean clearCart(Integer userId);
	
	List<CartListDto> selectUserCart(Integer userId);
}
