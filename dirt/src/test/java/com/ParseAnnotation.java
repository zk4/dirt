//package com;
//
//
//import com.zk.entity.User;
//import org.junit.jupiter.api.Test;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//public class ParseAnnotation {
//
//    @Test
//    void parse() {
//        Annotation[] annotations = User.class.getAnnotations();
//        for (Annotation annotation : annotations) {
//            System.out.println(annotation);
//        }
//
//        Method[] declaredMethods = User.class.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            for (Annotation declaredAnnotation : declaredMethod.getDeclaredAnnotations()) {
//                System.out.println(declaredAnnotation);
//            }
//        }
//
//        Field[] declaredFields = User.class.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations()) {
//                System.out.println(declaredAnnotation);
//                if(declaredAnnotation instanceof DirtFiled){
//                    System.out.println("found");
//                }
//            }
//        }
//
//    }
//
//    //@Test
//    //void getFiles() {
//    //    Field[] declaredFields = Layout.class.getDeclaredFields();
//    //    for (Field declaredField : declaredFields) {
//    //        DirtFiled dirtFiled = declaredField.getDeclaredAnnotation(DirtFiled.class);
//    //        System.out.println(dirtFiled.chsName());
//    //    }
//    //}
//}
