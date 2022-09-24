
package com.zk.dirt.annotation;

import com.zk.dirt.core.eDirtViewType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface DirtEntity {

	// 名字
	String value() default "";

	// 展现形式
	eDirtViewType viewType() default  eDirtViewType.Table;

	// 是否能从目录接口里拿到
	boolean visiable() default  true;
}
