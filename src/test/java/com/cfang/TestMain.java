package com.cfang;

import org.apache.commons.lang3.StringUtils;

import com.cfang.common.ShopConstants;

/**
 * describe：
 * @author cfang 2020年7月10日
 */
public class TestMain {

	public static void main(String[] args) {
		System.out.println(desensitizedPhoneNumber("15900665997"));
		System.out.println(desensitizedIdNumber("342425654523128456"));
		System.out.println(desensitizedIdNumber("123451234512345"));
		
		System.out.println(ShopConstants.orderStatus.C.getStatus());
	}
	
	private static String desensitizedPhoneNumber(String phoneNumber){
        if(StringUtils.isNotEmpty(phoneNumber)){
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return phoneNumber;
    }
	
	private static String desensitizedIdNumber(String idNumber){
        if (StringUtils.isNotEmpty(idNumber)) {
            if (idNumber.length() == 15){
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1******$2");
            }
            if (idNumber.length() == 18){
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1*********$2");
            }
            }
        return idNumber;
    }
}
