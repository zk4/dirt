package com.zk.dirt.annotation;

import com.zk.dirt.experiment.ColProps;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.dirt.core.eUIType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface DirtSubmit {
    // UI 排序
    int index() default 0;
    String placeholder() default  "";
    eSubmitWidth width() default  eSubmitWidth.MD;
    Class<? extends ColProps> colProps() default ColProps.class;//	ColProps	在开启 grid 模式时传递给 Col
    // 如果为 none，则利用 DirtField 的值
    eUIType valueType() default eUIType.none;
    //DirtFomItemRule[] rules() default  {};
}