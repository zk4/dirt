//package com.zk;
//
//import com.zk.demoentity.GithubBug;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class GenericTypeInfo {
//    java.util.List<GithubBug> fooList = new ArrayList<GithubBug>();
//    java.util.Map<String,GithubBug> map = new HashMap<>();
//
//    //static Type getParameterizedType(Field field){
//    //    Type type = field.getGenericType();
//    //    System.out.println("type: " + type);
//    //    if (type instanceof ParameterizedType) {
//    //        ParameterizedType pt = (ParameterizedType) type;
//    //        //System.out.println("raw type: " + pt.getRawType());
//    //        //System.out.println("owner type: " + pt.getOwnerType());
//    //        //System.out.println("actual type args:");
//    //        for (Type t : pt.getActualTypeArguments()) {
//    //            System.out.println("    " + t);
//    //        }
//    //    }
//    //
//    //}
//    public static void main(String[] args) throws Exception {
//        Field field = GenericTypeInfo.class.getDeclaredField("map");
//
//        Type type = field.getGenericType();
//        System.out.println("type: " + type);
//        if (type instanceof ParameterizedType) {
//            ParameterizedType pt = (ParameterizedType) type;
//            System.out.println("raw type: " + pt.getRawType());
//            System.out.println("owner type: " + pt.getOwnerType());
//            System.out.println("actual type args:");
//            for (Type t : pt.getActualTypeArguments()) {
//                System.out.println("    " + t);
//            }
//        }
//
//
//        System.out.println();
//
//        Object obj = field.get(new GenericTypeInfo());
//        System.out.println("obj: " + obj);
//        System.out.println("obj class: " + obj.getClass());
//    }
//
//    static class Foo {}
//
//    static class Bar extends Foo {}
//}