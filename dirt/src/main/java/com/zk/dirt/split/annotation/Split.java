package com.zk.dirt.split.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Split {
    int sizePrePartition() default 20;

    int taskPreThread() default 3;

    String paramSplitter() default "";

    String executor() default "defaultSplitExecutor";

    String resultMerger() default "";
}
