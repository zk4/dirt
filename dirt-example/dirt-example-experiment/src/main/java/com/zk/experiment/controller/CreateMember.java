package com.zk.experiment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.service.DirtService;
import com.zk.experiment.entity.Member;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CreateMember {
    @Autowired
    DirtService dirtService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/mmeber/create")
    @SneakyThrows
    public void createMember(@RequestBody  CreateMemberReq req)  {
        Map map = objectMapper.convertValue(req, Map.class);
        dirtService.create(Member.class.getName(),map);
    }

    @Data
    public static  class CreateMemberReq {
        String name;
    }

}
