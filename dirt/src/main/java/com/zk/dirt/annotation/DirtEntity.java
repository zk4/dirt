
package com.zk.dirt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface DirtEntity {

	String value() default "";
	enum eType  {
		// 二维结构
		Table,
		// 树结构
		Tree,
	}
	eType type() default  eType.Table;
}
