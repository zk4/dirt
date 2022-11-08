package com.zk.dirt.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.conf.DirtQueryFilter;
import com.zk.dirt.core.DirtFieldType;
import com.zk.dirt.core.DirtViewType;
import com.zk.dirt.core.Option;
import com.zk.dirt.service.DirtService;
import com.zk.dirt.wrapper.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DirtController {

    @Autowired
    DirtService dirtService;

    @Autowired
    ObjectMapper objectMapper;

    //@PostMapping("/dirt/upload")
    //@ApiOperation(value = "上传文件")
    //public String handleFileUpload(@RequestParam("file") MultipartFile file) {
    //    if (resourceUploader == null) {
    //        throw new RuntimeException("没实现 iResourceUploader, 无法上传");
    //    }
    //    return  resourceUploader.store(file);
    //}


    @PostMapping(value = "/dirt/action", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "执行动作")
    @Transactional
    public String action(@RequestBody ActionReq req) throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        String entityName = req.entityName;
        Long id = req.id;
        String actionName = req.actionName;
        Map args = req.args;
        dirtService.action(entityName, actionName, id, args);
        return objectMapper.writeValueAsString(Result.ok());
    }

    @Data
    @ApiModel("ActionReq")
    static class ActionReq {
        @ApiModelProperty(value = "实体名", required = true)
        String entityName;
        @ApiModelProperty(value = "动作名", required = true)
        String actionName;
        @ApiModelProperty(value = "id", required = true)
        Long id;
        @ApiModelProperty("参数")
        Map args;
    }


    @PostMapping(value = "/dirt/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建数据")
    @Transactional
    public String create(@RequestParam(name = "entityName") String entityName, @RequestBody Map body) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        dirtService.create(entityName, body);
        return objectMapper.writeValueAsString(Result.ok());
    }

    @PostMapping(value = "/dirt/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新数据")
    @Transactional
    public String update(@RequestParam(name = "entityName") String entityName, @RequestBody Map body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        dirtService.update(entityName, body);
        return objectMapper.writeValueAsString(Result.ok());
    }

    @PostMapping(value = "/dirt/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据 id 删除")
    @Transactional
    public String deleteByid(@RequestBody DeleteByIdReq req) throws ClassNotFoundException, JsonProcessingException {
        String entityName = req.entityName;
        Long id = req.id;
        dirtService.deleteById(entityName, id);
        return objectMapper.writeValueAsString(Result.ok());
    }

    @Data
    @ApiModel("DeleteByIdReq")
    static class DeleteByIdReq {
        @ApiModelProperty("实体名")
        String entityName;
        @ApiModelProperty("id")
        Long id;
    }

    @PostMapping(value = "/dirt/deleteByIds", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据 ids 批量删除")
    @Transactional
    public String deleteByids(@RequestBody DeleteByidsReq req) throws ClassNotFoundException, JsonProcessingException {
        String entityName = req.entityName;
        List<Long> ids = req.ids;
        dirtService.deleteByIds(entityName, ids);
        return objectMapper.writeValueAsString(Result.ok());

    }

    @Data
    @ApiModel("DeleteByidsReq")
    static class DeleteByidsReq {
        @ApiModelProperty("实体名")
        String entityName;
        @ApiModelProperty("ids")
        List<Long> ids;
    }

    @PostMapping(value = "/dirt/getDatas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取分页数据")
    @Transactional(readOnly = true)
    public String page(@RequestBody DirtQueryFilter reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException, JsonProcessingException {
        Page<Object> page = dirtService.page(reqFilter, entityName, pageable);
        return objectMapper.writeValueAsString(Result.success(page));
    }

    @PostMapping(value = "/dirt/getFullDatas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取全量数据")
    @Transactional(readOnly = true)
    public String fullData(@RequestBody DirtQueryFilter reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException, JsonProcessingException {
        List<Object> objects = dirtService.fullData(reqFilter, entityName, pageable);
        Result<List<Object>> success = Result.success(objects);
        return objectMapper.writeValueAsString(success);

    }

    @GetMapping(value = "/dirt/getDataByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据 code 获取单条数据")
    @SneakyThrows
    @Transactional(readOnly = true)

    public String getByCode(@RequestParam(name = "entityName") String entityName, @RequestParam(name = "code") String code) {
        Object byId = dirtService.getByCode(entityName, code);
        Result<Object> success = Result.success(byId);
        return objectMapper.writeValueAsString(success);

    }


    @GetMapping(value = "/dirt/getData", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据 id 获取单条数据")
    @Transactional(readOnly = true)
    public String getById(@RequestParam(name = "entityName") String entityName, @RequestParam(name = "id") Long id) throws JsonProcessingException {
        Object byId = dirtService.getById(entityName, id);
        Result<Object> success = Result.success(byId);
        return objectMapper.writeValueAsString(success);

    }

    @GetMapping(value = "/dirt/getEntitySchema", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取标准 schema")
    public String getTableHeaders(@RequestParam(name = "entityName") String entityName) throws JsonProcessingException {
        List<DirtFieldType> tableHeaders = dirtService.getTableHeaders(entityName);
        Result<List<DirtFieldType>> success = Result.success(tableHeaders);
        return objectMapper.writeValueAsString(success);

    }


    @GetMapping(value = "/dirt/getTablesNames", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取表名")
    public String getTablesNames() throws JsonProcessingException {
        Set<String> tablesNames = dirtService.getTablesNames();
        Result<Set<String>> success = Result.success(tablesNames);
        return objectMapper.writeValueAsString(success);
    }


    @GetMapping(value = "/dirt/getTableMaps", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取所有表信息")
    public String getTableMaps() throws JsonProcessingException {
        Map<String, DirtViewType> tableMaps = dirtService.getTableMaps();
        Result<Map<String, DirtViewType>> success = Result.success(tableMaps);
        return objectMapper.writeValueAsString(success);
    }

    @PostMapping(value = "/dirt/getDirtFieldType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取 DirtFieldType")
    public String getDirtField(@RequestBody GetDirtFieldReq req) throws JsonProcessingException {
        DirtFieldType dirtField = dirtService.getDirtField(req.entityName, req.fieldName);
        Result<DirtFieldType> success = Result.success(dirtField);
        return objectMapper.writeValueAsString(success);
    }

    @Data
    @ApiModel("GetDirtFieldReq")
    static class GetDirtFieldReq {
        @ApiModelProperty("实体名")
        String entityName;

        @ApiModelProperty("field")
        String fieldName;

        @ApiModelProperty("参数")
        Map args;
    }

    @PostMapping(value = "/dirt/getOptions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取 动态 options")
    public String getOptions(@RequestBody OptionReq req) throws JsonProcessingException, NoSuchFieldException, ClassNotFoundException {
        List<Option> options = dirtService.getOptions(req.entityName, req.subKey, req.args);
        return objectMapper.writeValueAsString(Result.success(options));
    }

    @Data
    @ApiModel("OptionReq")
    static class OptionReq {
        @ApiModelProperty("实体名")
        String entityName;

        @ApiModelProperty("subKey")
        String subKey;

        @ApiModelProperty("参数")
        Map args;
    }
    //
    //@Data
    //@Builder
    //@ApiModel("OptionRes")
    //static class Option {
    //    @ApiModelProperty("label")
    //    String label;
    //
    //    @ApiModelProperty("value")
    //    String value;
    //}
}
