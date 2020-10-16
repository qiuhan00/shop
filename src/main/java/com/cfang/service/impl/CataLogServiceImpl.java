package com.cfang.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.cfang.annotation.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cfang.dto.IndexProductTree;
import com.cfang.entity.CatalogEntity;
import com.cfang.mapper.CataLogMapper;
import com.cfang.mapper.ProductMapper;
import com.cfang.service.CataLogService;
import com.google.common.collect.Lists;

/**
 * describe：
 * @author cfang 2020-7-8
 */
@Service
public class CataLogServiceImpl implements CataLogService{

	@Autowired
	private CataLogMapper cataLogMapper;
	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * 	缓存过期之后，如果多个线程同时请求对某个数据的访问，会同时去到数据库，导致数据库瞬间负荷增高。
	 *  Spring4.3为@Cacheable注解提供了一个新的参数“sync”（boolean类型，缺省为false），
	 * 	当设置它为true时，只有一个线程的请求会去到数据库，其他线程都会等待直到缓存可用。这个设置可以减少对数据库的瞬间并发访问。
	 */
	@Override
	@Cacheable(value = "catalogs", key = "'catalogs'", sync = true)
	@ApiLog(operatorType = "select", operatorModule = "产品", operatorDesc = "查询产品树")
	public List<IndexProductTree> selectIndexProduct() {
		List<CatalogEntity> catalogs = cataLogMapper.selectAll();
		return assembly(catalogs);
	}
	
	private List<IndexProductTree> assembly(List<CatalogEntity> catalogs){
		List<IndexProductTree> result = Lists.newArrayList();
		List<CatalogEntity> one = catalogs.stream().filter(obj -> 1 == obj.getNodeLevel()).collect(Collectors.toList());
		one.forEach(obj -> {
			IndexProductTree tree = new IndexProductTree();
			tree.setCode(obj.getCode());
			tree.setName(obj.getName());
			List<CatalogEntity> second = catalogs.stream().filter(obj2 -> obj.getCode().equals(obj2.getParentCode())).collect(Collectors.toList());
			List<IndexProductTree> secondTree = Lists.newArrayList();
			second.forEach(obj2 -> {
				IndexProductTree node = new IndexProductTree();
				node.setCode(obj2.getCode());
				node.setName(obj2.getName());
//				node.setProducts(productMapper.selectByCataLogId(obj2.getId()));
				secondTree.add(node);
			});
			tree.setTrees(secondTree);
			result.add(tree);
		});
		return result;
	}
	
}
