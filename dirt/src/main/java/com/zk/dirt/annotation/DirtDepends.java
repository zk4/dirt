package com.zk.dirt.annotation;

import com.zk.dirt.intef.iDependProvider;

public @interface DirtDepends {
    String dependsOnColumn();
    Class<? extends iDependProvider> dependsProvider();
}