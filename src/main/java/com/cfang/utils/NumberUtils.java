package com.cfang.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @description：
 * @author cfang 2020年7月28日
 */
public class NumberUtils {

	/**
	 * 手机号正则脱敏：15012341234 -> 150****1234
	 * @param phoneNumber
	 * @return
	 */
	public static String desensitizedPhoneNumber(String phoneNumber){
        if(StringUtils.isNotEmpty(phoneNumber)){
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return phoneNumber;
    }
	/**
	 * 身份证前六后四脱敏
	 * @param idNumber
	 * @return
	 */
	public static String desensitizedIdNumber(String idNumber){
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
