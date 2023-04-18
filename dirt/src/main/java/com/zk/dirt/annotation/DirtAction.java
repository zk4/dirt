package com.zk.dirt.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({METHOD})
@Retention(RUNTIME)
public @interface DirtAction {
    String text();
    String desc() default  "";
    // 是否需要确认操作
    boolean confirm() default  false;
}