
package com.zk.config.tenant.aspect;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TenantAnnotationAspect.class)
public @interface EnableTenantAspect { }
