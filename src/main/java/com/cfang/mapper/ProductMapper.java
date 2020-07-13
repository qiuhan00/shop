package com.cfang.mapper;

import java.util.List;

import com.cfang.common.CommonMapper;
import com.cfang.entity.ProductEntity;

/**
 * describe：
 * @author cfang 2020-7-8
 */
public interface ProductMapper extends CommonMapper<ProductEntity>{

	List<ProductEntity> selectByCataLogId(Integer cataLogId);
	
}
