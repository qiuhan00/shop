package com.cfang.common;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @description 
 * @author cfang 2020年8月11日
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiIdempotent {

}
