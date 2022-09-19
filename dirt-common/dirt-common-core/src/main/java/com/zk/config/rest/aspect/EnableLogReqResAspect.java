
package com.zk.config.rest.aspect;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LogReqResAspect.class)
public @interface EnableLogReqResAspect { }
