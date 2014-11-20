package com.example.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体与数据库中表的对应关系 
 * @author flystar
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName 
{
	/**
	 * 数据库中表名
	 * @return
	 */
	String value();

}
