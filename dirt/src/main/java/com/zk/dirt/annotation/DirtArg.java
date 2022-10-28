package com.zk.dirt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface DirtArg {
    // 绑定参数名用，跟参数写一样就行，JVM 在 runtime 会对参数改名。 需要手绑
    String value();
}