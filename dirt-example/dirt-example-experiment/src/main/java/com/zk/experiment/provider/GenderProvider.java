//package com.zk.experiment.provider;
//
//import com.google.common.collect.ImmutableMap;
//import com.zk.dirt.intef.iNoArgDatasource;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GenderProvider implements iNoArgDatasource<String,ImmutableMap> {
//    @Override
//    public ImmutableMap<String,ImmutableMap> getSource() {
//        return ImmutableMap.of(
//                "",ImmutableMap.of("text", "全部", "status", ""),
//                "man", ImmutableMap.of("text", "男", "status", "man"),
//                "woman", ImmutableMap.of("text", "女", "status", "woman")
//        );
//    }
//
//    @Override
//    public String initialValue() {
//        return "man";
//    }
//}
