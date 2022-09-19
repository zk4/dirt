package com.zk.dirt.annotation;

import com.zk.dirt.core.eUIType;
import com.zk.dirt.core.eFilterOperator;

public @interface DirtSearch {

    String title() default "";
    // 如果为 none，则利用 DirtField 的值
    eUIType valueType() default eUIType.none;
    // 操作符
    eFilterOperator operator() default  eFilterOperator.EQUAL;

}