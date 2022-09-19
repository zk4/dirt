//package com.zk.hibernate.config.tenant;
//
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.hibernate.Session;
//import org.springframework.stereotype.Component;

// 方法详见： https://callistaenterprise.se/blogg/teknik/2020/10/17/multi-tenancy-with-spring-boot-part5/
// 这个不好整， 需要在类加载前用这个功能 @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
// 那就需要这样启动，没成功，懒得搞了 java -javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar -jar app.jar
// 感觉好处就是能够拦截 session，不用指定拦截位置，其实只要包够标准用  TenantFilterAspect2 足够

//@Aspect
//@Component
//public class TenantFilterAspect {
//
//    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
//    public void openSession() {
//    }
//
//    @AfterReturning(pointcut = "openSession()", returning = "session")
//    public void afterOpenSession(Object session) {
//        if (session != null && Session.class.isInstance(session)) {
//            final String tenantId = TenantContext.getTenantId();
//            if (tenantId != null) {
//                org.hibernate.Filter filter = ((Session) session).enableFilter("tenantFilter");
//                filter.setParameter("tenantId", tenantId);
//            }
//        }
//    }
//
//}