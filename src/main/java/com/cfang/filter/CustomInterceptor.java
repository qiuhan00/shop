package com.cfang.filter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：自定义 Mybatis 插件，自动设置 createTime 和 updatTime 的值，拦截 update 操作（添加和修改）
 * @author cfang 2020年7月24日
 */
@Intercepts({
	@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class CustomInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object object = invocation.getArgs()[1];
		//sql类型
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            Field fieldCreate = object.getClass().getField("createTime");
            fieldCreate.setAccessible(true);
            fieldCreate.set(object, new Date());
        }else{
            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            	try {
            		Field fieldUpdate = object.getClass().getField("updateTime");
            		fieldUpdate.setAccessible(true);
            		fieldUpdate.set(object, new Date());
				} catch (Exception e) {
					log.warn("SQL注入updateTime异常，method:{}", invocation.getMethod().getName());
				}
            }
        }
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		 
	}

}
