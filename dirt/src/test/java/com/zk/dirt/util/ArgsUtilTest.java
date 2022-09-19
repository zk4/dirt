//package com.zk.dirt.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.util.BeanUtil;
//import com.zk.member.entity.Member;
//import lombok.Data;
//import lombok.ToString;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.math.BigDecimal;
//import java.util.*;
//
////@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//
//class ArgsUtilTest {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    public static void foo(Object... args) {
//        System.out.println("foo: " + Arrays.toString(args));
//    }
//
//    public static void bar(Object[] args) {
//        System.out.println("bar: " + Arrays.toString(args));
//    }
//
//
//    public String hello(String name, Integer age) {
//
//        return name + age;
//    }
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void testReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        Method hello = ArgsUtilTest.class.getMethod("hello", String.class, Integer.class);
//        LinkedHashMap<String, Object> args = new LinkedHashMap<>();
//        args.put("name", "zk");
//        args.put("age", 12);
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,hello.getParameters(), args);
//        ArgsUtilTest varargsTest = new ArgsUtilTest();
//        String ret = (String) hello.invoke(varargsTest, objects);
//
//
//        Assertions.assertEquals(ret, "zk12");
//    }
//
//    @Test
//    public void testPrimitive() {
//        // only foo can be called with variable arity arguments
//        foo(1, 2, 3);
//        // bar( 4, 5, 6 ); // won't compile
//
//        // both methods can be called with object arrays
//        foo(new Object[]{7, 8, 9});
//        bar(new Object[]{10, 11, 12});
//
//        // so both can be called with List#toArray results
//        foo(Arrays.asList(13, 14, 15).toArray());
//        bar(Arrays.asList(16, 17, 18).toArray());
//    }
//
//
//    public String updateEntity(Member member) {
//        return member.getName();
//    }
//
//    @Test
//    public void testEntity() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        Method hello = ArgsUtilTest.class.getMethod("updateEntity", Member.class);
//        LinkedHashMap<String, Object> args = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> member = new LinkedHashMap<>();
//
//        member.put("name", "zk");
//        args.put("member", member);
//
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,hello.getParameters(), args);
//        ArgsUtilTest varargsTest = new ArgsUtilTest();
//        String ret = (String) hello.invoke(varargsTest, objects);
//
//
//        Assertions.assertEquals(ret, "zk");
//
//    }
//
//    @Data
//    @ToString
//    static class HelloObject {
//        String name;
//        Member member;
//    }
//
//    public String updateNormalObject(HelloObject obj) {
//        System.out.println(obj);
//        return obj.getName();
//    }
//
//    @Test
//    public void testUpdateNormalObject() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        Method hello = ArgsUtilTest.class.getMethod("updateNormalObject", HelloObject.class);
//        LinkedHashMap<String, Object> args = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> helloObject = new LinkedHashMap<>();
//        helloObject.put("name", "zk");
//        args.put("obj", helloObject);
//        LinkedHashMap<String, Object> member = new LinkedHashMap<>();
//        member.put("name", "memberzk");
//        helloObject.put("member", member);
//
//
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,hello.getParameters(), args);
//        ArgsUtilTest varargsTest = new ArgsUtilTest();
//        String ret = (String) hello.invoke(varargsTest, objects);
//
//        Assertions.assertEquals(ret, "zk");
//
//    }
//
//    @Test
//    public void tesBoundary() {
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            ArgsUtil.mapToArray(objectMapper,null, null);
//        }, "parameters can not be null");
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            ArgsUtil.mapToArray(objectMapper,null, new HashMap());
//        }, "parameters can not be null");
//
//        Assertions.assertEquals(ArgsUtil.ZERO_OBJECT, ArgsUtil.mapToArray(objectMapper,new Parameter[0], null));
//    }
//
//    @Data
//    @ToString
//    static class MixData {
//        Member member;
//        Map map;
//        List list;
//        String string;
//        int i;
//        Integer anInteger;
//        long l;
//        Long anLong;
//        Double d;
//        Double anDouble;
//        BigDecimal bigDecimal;
//        String[] arrays;
//    }
//
//    public MixData callMixed(MixData mixData) {
//        return mixData;
//    }
//
//    @Test
//    @DisplayName("map->json->map->entity")
//    public void testMixEntityAndAllFromJsonStr() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException {
//        Method hello = ArgsUtilTest.class.getMethod("callMixed", MixData.class);
//        LinkedHashMap<String, Object> args = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> mixData = new LinkedHashMap<>();
//        args.put("mixData", mixData);
//        LinkedHashMap<String, Object> member = new LinkedHashMap<>();
//
//        member.put("name", "zk");
//        member.put("id", 23);
//        mixData.put("member", member);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("map", "hashmap");
//        mixData.put("map", map);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("list");
//        mixData.put("list", list);
//        mixData.put("string", "string");
//        mixData.put("i", 1);
//        mixData.put("anInteger", 2);
//        mixData.put("l", 3);
//        mixData.put("anLong", 4);
//
//        mixData.put("d", "5.6");
//        mixData.put("anDouble", "6.7");
//        mixData.put("bigDecimal", "7.89");
//
//        mixData.put("arrays", new String[]{"a", "b"});
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String string = objectMapper.writeValueAsString(args);
//        System.out.println(string);
//        Map map1 = objectMapper.readValue(string, Map.class);
//
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,hello.getParameters(), map1);
//        ArgsUtilTest varargsTest = new ArgsUtilTest();
//        Object invoke = hello.invoke(varargsTest, objects);
//        System.out.println(invoke);
//
//
//    }
//
//    @Test
//    public void testMixEntityAndAll() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method hello = ArgsUtilTest.class.getMethod("callMixed", MixData.class);
//        LinkedHashMap<String, Object> args = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> mixData = new LinkedHashMap<>();
//        args.put("mixData", mixData);
//        LinkedHashMap<String, Object> member = new LinkedHashMap<>();
//
//        member.put("name", "zk");
//        member.put("id", 23);
//        mixData.put("member", member);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("map", "hashmap");
//        mixData.put("map", map);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("list");
//        mixData.put("list", list);
//        mixData.put("string", "string");
//        mixData.put("i", 1);
//        mixData.put("anInteger", 2);
//        mixData.put("l", 3);
//        mixData.put("anLong", 4);
//        mixData.put("d", "5.6");
//        mixData.put("anDouble", "6.7");
//        mixData.put("bigDecimal", "7.89");
//
//        mixData.put("arrays", new String[]{"a", "b"});
//
//
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,hello.getParameters(), args);
//        ArgsUtilTest varargsTest = new ArgsUtilTest();
//        Object invoke = hello.invoke(varargsTest, objects);
//        System.out.println(invoke);
//
//
//    }
//
//    @Data
//    static class CamelCaseData {
//        Integer anInteger;
//        //Long Gbc;
//    }
//
//    @Test
//    void testcamelCaseProblem() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        HashMap<String, Object> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("anInteger", 111);
//        CamelCaseData camelCaseData = objectMapper.convertValue(stringStringHashMap, CamelCaseData.class);
//        System.out.println(camelCaseData);
//    }
//
//    @Test
//    void testGetterName() {
//        String ret = BeanUtil.okNameForRegularGetter(null, "getAInteger", true);
//        System.out.println(ret);
//        ret = BeanUtil.okNameForRegularGetter(null, "getAInteger", false);
//        System.out.println(ret);
//        ret = BeanUtil.okNameForRegularGetter(null, "getAnInteger", true);
//        System.out.println(ret);
//        ret = BeanUtil.okNameForRegularGetter(null, "getAnInteger", false);
//        System.out.println(ret);
//    }
//
//    @Test
//    void isPrimitive() {
//
//        System.out.println(int.class.isPrimitive());
//    }
//
//
//    public void  callPrimitive(int a , long b){
//        System.out.println(a);
//        System.out.println(b);
//    }
//
//    @Test
//    void testCallPrimitive() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method method= ArgsUtilTest.class.getMethod("callPrimitive", int.class,long.class);
//        HashMap<Object, Object> args = new HashMap<>();
//        args.put("a", "1");
//        args.put("b", "2");
//        Object[] objects = ArgsUtil.mapToArray(objectMapper,method.getParameters(), args);
//        method.invoke(new ArgsUtilTest(),objects);
//
//
//    }
//}