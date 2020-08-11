package com.cfang.httpInterface;

import com.dtflys.forest.annotation.DataParam;
import com.dtflys.forest.annotation.Request;

/**
 * @description 
 * @author cfang 2020年8月11日
 */
public interface MapAreaClient {

	@Request(url = "${0}?extensions=base&subdistrict=4",dataType = "json")
	String getVal(String baseUrl, @DataParam("key") String key, @DataParam("keywords") String keywords);
}
