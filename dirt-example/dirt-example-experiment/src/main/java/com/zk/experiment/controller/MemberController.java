package com.zk.experiment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.conf.DirtQueryFilter;
import com.zk.dirt.service.DirtService;
import com.zk.dirt.wrapper.Result;
import com.zk.experiment.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = {"会员"})
public class MemberController {
    @Autowired
    DirtService dirtService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/mmeber/create")
    @ApiOperation(value = "创建会员")
    @SneakyThrows
    public void createMember(@RequestBody  CreateMemberReq req)  {
        Map map = objectMapper.convertValue(req, Map.class);
        dirtService.create(Member.class.getName(),map);
    }

    @Data
    public static  class CreateMemberReq {
        String name;
    }

    @PostMapping(value = "/mmeber/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "搜索会员")
    @SneakyThrows
    public String searchMember(@RequestBody DirtQueryFilter reqFilter,  Pageable pageable)  {
        Page<Object> page = dirtService.page(reqFilter, Member.class.getName(), pageable);
        return objectMapper.writeValueAsString(Result.success(page));
    }



}
