package com.cfang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cfang.exception.BusyException;
import com.cfang.exception.InvalidParamException;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("reqUrl", String.join(":", "reqUrl", request.getRequestURL()));
		if(exception instanceof NoHandlerFoundException) {
			mv.addObject("msg", String.join(":", "errMsg", "404 unknown url"));
		}else if (exception instanceof InvalidParamException) {
			InvalidParamException validException = (InvalidParamException) exception;
			mv.addObject("msg", "errMsg:" + String.join("-", validException.getErrorId()+"", validException.getMessage()));
		}else if (exception instanceof BusyException) {
			BusyException busyException = (BusyException) exception;
			mv.addObject("msg", "errMsg:" +  String.join("-", busyException.getErrorId()+"", busyException.getMessage()));
		}else {
			mv.addObject("msg", String.join(":", "errMsg", "未知异常，" + exception.getMessage()));
		}
		mv.setViewName("error");
		return mv;
	}
}
