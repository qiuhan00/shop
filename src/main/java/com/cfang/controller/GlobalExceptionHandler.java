package com.cfang.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cfang.exception.AjaxException;
import com.cfang.exception.BusyException;
import com.cfang.exception.InvalidParamException;

import javassist.compiler.ast.NewExpr;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest request, Exception exception, HandlerMethod handlerMethod) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("reqUrl", String.join(":", "reqUrl", request.getRequestURL()));
//		boolean isAjax = isAjax(request);
//		Method m = handlerMethod.getMethod();
//		Class<?> clazz = handlerMethod.getBeanType();
//		boolean isRestReq = (m.getAnnotation(ResponseBody.class)!=null
//                ||clazz.getAnnotation(ResponseBody.class)!=null
//                ||clazz.getAnnotation(RestController.class)!=null);
//		if(isAjax || isRestReq) {
//			return AjaxException.errorException(exception.getMessage());
//		}
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
	
	private boolean isAjax(HttpServletRequest req) {
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }
}
