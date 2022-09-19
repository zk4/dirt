package com.zk.dirt.util;

import com.zk.dirt.annotation.DirtEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;

class PackageUtilTest {

    @Test
    void findClassAnnotationMethods() {

    }

    @Test
    void findAnnotationMethods() {
    }

    @Test
    void findClassAnnotationClasses() {
        Set<Class> classAnnotationClasses = PackageUtil.findClassAnnotationClasses("com.zk.entity", DirtEntity.class);
        Class next = classAnnotationClasses.iterator().next();
        System.out.println(next.getName());
        System.out.println(next.getSimpleName());
        System.out.println(next.getCanonicalName());
        System.out.println(next.getTypeName());
    }



}