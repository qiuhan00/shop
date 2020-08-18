package com.cfang.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description 
 * @author cfang 2020年8月18日
 */
@Data
@Accessors(chain = true)
public class OrderResp {

	private String payName;
	private String orderNo;
}
