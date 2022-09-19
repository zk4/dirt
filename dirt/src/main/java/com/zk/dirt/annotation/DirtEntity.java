
package com.zk.dirt.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface DirtEntity {
	String name() default "";

	// 默认每页数据
	int pageSize() default  10;
	boolean treeView() default  false;

}
