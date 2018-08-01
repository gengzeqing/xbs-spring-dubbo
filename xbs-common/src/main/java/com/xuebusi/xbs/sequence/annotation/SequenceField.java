package com.xuebusi.xbs.sequence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要增加序列的字段
*/ 
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SequenceField {

}
