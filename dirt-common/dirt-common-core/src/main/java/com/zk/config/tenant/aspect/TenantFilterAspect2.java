//package com.zk.hibernate.config.tenant;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.hibernate.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//
//@Aspect
//@Component
//public class TenantFilterAspect2 {
//
//    @Autowired
//    EntityManager entityManager;
//    // 增加租户过滤参数
//    @Before("execution(* com.zk.hibernate.modules.*.repo.*.*(..))")
//    public void beforeQueryExecution(JoinPoint pjp) throws Throwable {
//        String tenantId = TenantContext.getTenantId();
//        org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
//        filter.setParameter("tenantId",  tenantId);
//        filter.validate();
//    }
//}