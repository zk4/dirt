package com.zk.ddd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.service.DirtService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"会员"})
public class MemberController {
    @Autowired
    DirtService dirtService;

    @Autowired
    ObjectMapper objectMapper;
    //
    //@PostMapping("/mmeber/create")
    //@ApiOperation(value = "创建会员")
    //@SneakyThrows
    //public void createMember(@RequestBody  CreateMemberReq req)  {
    //    Map map = objectMapper.convertValue(req, Map.class);
    //    dirtService.create(Member.class.getName(),map);
    //}
    //
    //@Data
    //public static  class CreateMemberReq {
    //    String name;
    //}
    //
    //@PostMapping(value = "/mmeber/search", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ApiOperation(value = "搜索会员")
    //@SneakyThrows
    //public String searchMember(@RequestBody DirtQueryFilter reqFilter,  Pageable pageable)  {
    //    Page<Object> page = dirtService.page(reqFilter, Member.class.getName(), pageable);
    //    return objectMapper.writeValueAsString(Result.success(page));
    //}



}
