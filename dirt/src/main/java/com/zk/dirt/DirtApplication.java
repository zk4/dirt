//package com.zk.dirt;
//
//import com.zk.dirt.annotation.DirtScan;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.type.AnnotationMetadata;
//import org.springframework.util.ClassUtils;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Stream;
//
//public class DirtApplication implements ImportBeanDefinitionRegistrar {
//
//    private static Class<?> primarySource;
//
//    private static final Set<String> scanPackage = new HashSet<>();
//
//    public static Class<?> getPrimarySource() {
//        return primarySource;
//    }
//
//    public static String[] getScanPackage() {
//        return scanPackage.toArray(new String[0]);
//    }
//
//    @SneakyThrows
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        Class<?> clazz = ClassUtils.forName(importingClassMetadata.getClassName(), ClassUtils.getDefaultClassLoader());
//        Optional.ofNullable(clazz.getAnnotation(SpringBootApplication.class)).ifPresent(it -> primarySource = clazz);
//        DirtScan eruptScan = clazz.getAnnotation(DirtScan.class);
//        if (eruptScan.value().length == 0) {
//            scanPackage.add(clazz.getPackage().getName());
//        } else {
//            Stream.of(eruptScan.value()).filter(pack -> !pack.equals("com.zk.dirt.entity")).forEach(scanPackage::add);
//        }
//    }
//}
