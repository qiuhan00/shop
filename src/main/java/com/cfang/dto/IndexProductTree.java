package com.cfang.dto;

import java.io.Serializable;
import java.util.List;

import com.cfang.entity.ProductEntity;

import lombok.Data;

/**
 * describe：
 * @author cfang 2020-7-8
 */
@Data
public class IndexProductTree implements Serializable{

	private String name; //类别名称
	private String code;
	private List<IndexProductTree> trees;
	private List<ProductEntity> products;
}
