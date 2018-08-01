package com.xuebusi.xbs.annotation;

import com.xuebusi.xbs.datasource.DataSource;

import java.lang.annotation.*;

/**
 * 数据源注解
 */
//@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MarkMasterDataSource {
	String sourceType() default DataSource.MASTER;
}
 