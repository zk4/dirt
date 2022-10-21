//package com.zk;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zk.config.rest.DoNotWrapperResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TestController {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @PostMapping("/datetest")
//    @DoNotWrapperResult
//    public String timerequest(@RequestBody DataHolder dataHolder) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(dataHolder);
//    }
//
//    @PostMapping("/datetest2")
//    @DoNotWrapperResult
//    public String timerequest2(@RequestBody DataHolder dataHolder) throws JsonProcessingException {
//        return this.objectMapper.writeValueAsString(dataHolder);
//    }
//
//}
