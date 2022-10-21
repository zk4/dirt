//package com.zk;
//
//import com.google.common.collect.ImmutableMap;
//import com.zk.config.rest.Result;
//import com.zk.dirt.rest.DirtController;
//import com.zk.experiment.Member;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.beans.IntrospectionException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Arrays;
//import java.util.HashMap;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = ExperimentApplication.class)
//@Rollback(value = true)
//public class ControllerTest {
//
//    @Autowired
//    DirtController dirtController;
//
//
//
//    @Test
//    @Transactional
//    void controllerCreateTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IntrospectionException, InvocationTargetException {
//        HashMap hashMap = new HashMap();
//        hashMap.put("name", "刘正青");
//        hashMap.put("nickname", "zk");
//        hashMap.put("cards", Arrays.asList(ImmutableMap.of("id",1)));
//        hashMap.put("coupons", Arrays.asList(ImmutableMap.of("id",1)));
//        hashMap.put("myGroups", Arrays.asList(ImmutableMap.of("id",1)));
//
//        dirtController.create("com.zk.experiment.Member",hashMap);
//    }
//
//    @Test
//    @Transactional
//    void controllerUpdateTest() throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InvocationTargetException {
//        HashMap hashMap = new HashMap();
//        hashMap.put("name", "刘正青4");
//        hashMap.put("id", "31");
//        hashMap.put("nickname", "zk");
//
//
//        String entityName = "com.zk.experiment.Member";
//        dirtController.update(entityName,hashMap);
//
//        Result byId = dirtController.getById(entityName, 31L);
//        Member data = (Member) byId.getData();
//        System.out.println(data.getCards());
//        System.out.println(data.getName());
//        Assertions.assertEquals("刘正青4", data.getName());
//
//    }
//}
