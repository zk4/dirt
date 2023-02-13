//package com.zk.mall.provider;
//
//import com.google.common.collect.ImmutableMap;
//import com.zk.dirt.core.DirtEnumValue;
//import com.zk.dirt.intef.iDataSource;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class StatusProvider implements iDataSource<String, DirtEnumValue> {
//
//
//
//    @Override
//    public List<DirtEnumValue> getSource(String dependsEnityName, String dependsFiledName, Map<String, Object> args) {
//        return List.of(
//                "", new DirtEnumValue("全部", "", "white"),
//                "close", new DirtEnumValue("关闭", "close", "gray"),
//                "running", new DirtEnumValue("运行中", "running", "blue"),
//                "online", new DirtEnumValue("已上线", "online", "green"),
//                "error", new DirtEnumValue("异常", "error", "red")
//        );
//    }
//
//    @Override
//    public String initialValue() {
//        return "";
//    }
//}
