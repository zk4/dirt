package com.zk.config.tenant.aspect;

import com.zk.config.tenant.TenantContext;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@Aspect
//@Component
public class TenantAnnotationAspect {
    private static final String FILTER_NAME = "tenantFilter";
    @Autowired
    EntityManager entityManager;

    @SneakyThrows
    @Around("@annotation(com.zk.config.tenant.TenantFilter)")
    public Object aspect(ProceedingJoinPoint joinPoint) {
        Session session = entityManager.unwrap(Session.class);
        try {
            String tenantId = TenantContext.getTenantId();
            if(tenantId==null){
             throw new RuntimeException("租户不存在");
            }
            session.enableFilter(FILTER_NAME).setParameter("tenantId", tenantId);
            return joinPoint.proceed();
        } finally {
            session.disableFilter(FILTER_NAME);
        }
    }
}