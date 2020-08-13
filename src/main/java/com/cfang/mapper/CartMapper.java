package com.cfang.mapper;

import java.util.List;

import com.cfang.common.CommonMapper;
import com.cfang.dto.CartListDto;
import com.cfang.entity.CartEntity;

/**
 * @description：
 * @author cfang 2020年7月14日
 */
public interface CartMapper extends CommonMapper<CartEntity>{

	int clearCart(Integer userId);
	
	List<CartListDto> selectUserCart(Integer userId);
	
	int updateStatus(CartEntity entity); 
}
