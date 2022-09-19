package com.zk.config.tenant;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 激活hibernate filter必须要开启事务
@Transactional
public @interface TenantFilter {

    boolean readOnly() default true;

}