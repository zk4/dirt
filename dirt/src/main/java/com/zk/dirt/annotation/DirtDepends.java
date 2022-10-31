package com.zk.dirt.annotation;

import com.zk.dirt.intef.iDependProvider;

public @interface DirtDepends {
    // TODO： 还未实现 :(
    // 当 onColumn 值变化时，触发 iDependProvider 计算

    String onColumn();
    Class<? extends iDependProvider> dependsProvider();
}