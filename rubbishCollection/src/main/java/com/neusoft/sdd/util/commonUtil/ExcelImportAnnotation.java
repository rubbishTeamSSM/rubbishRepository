package com.neusoft.sdd.util.commonUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface ExcelImportAnnotation {
	String propertyName() default "";   
	String fieldName() default ""; 
	String fieldType() default "varchar";
	String chineseName() default ""; 
	boolean allowNull() default true;
	boolean autoIncrementKey() default false;
}
