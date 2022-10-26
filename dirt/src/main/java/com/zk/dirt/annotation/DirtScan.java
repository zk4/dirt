
package com.zk.dirt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * For performance, you can only set it on Main class.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface DirtScan {

	// DirtEntity scan path
	String[] value() default {"com.zk"};
}
