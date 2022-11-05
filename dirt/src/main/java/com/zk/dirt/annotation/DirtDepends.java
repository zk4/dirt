package com.zk.dirt.annotation;

import com.zk.dirt.DependsProvider;
import com.zk.dirt.intef.iDenpendsWithArgsDataSource;

public @interface DirtDepends {
    Class[] onEntity() default {};
    String onColumn();
    Class<? extends iDenpendsWithArgsDataSource> dataSource() default DependsProvider.class;
}