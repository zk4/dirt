package com.zk.dirt.annotation;

import com.zk.dirt.DependsProvider;
import com.zk.dirt.intef.iDataSource;

public @interface DirtDepends {
    Class[] onEntity() default {};
    String onColumn() default  "";
    Class<? extends iDataSource> dataSource() default DependsProvider.class;
}