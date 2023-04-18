package com.zk.dirt.annotation;

import com.zk.dirt.DependsProvider;
import com.zk.dirt.intef.iDataSource;

public @interface DirtDataSource {
    Class[] onEntity() default {};
    String dependsColumn() default  "";
    Class<? extends iDataSource> value() default DependsProvider.class;
}