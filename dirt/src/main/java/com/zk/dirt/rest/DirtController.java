package com.zk.dirt.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.conf.DirtQueryFilter;
import com.zk.dirt.core.*;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.entity.iID;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.intef.iResourceUploader;
import com.zk.dirt.util.ArgsUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RestController
public class DirtController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DirtContext dirtContext;

    @Autowired
    iPersistProxy persistProxy;

    @Autowired
    EntityManager entityManager;

    @Autowired(required = false)
    iResourceUploader resourceUploader;


    @PostMapping("/dirt/upload")
    @ApiOperation(value = "上传文件")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (resourceUploader == null) {
            throw new RuntimeException("没实现 iResourceUploader, 无法上传");
        }
        return  resourceUploader.store(file);
    }


    @PostMapping(value = "/dirt/action", produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "执行动作")
    @Transactional
    public String action(@RequestBody ActionReq req) throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        String entityName = req.entityName;
        Long id = req.id;
        String actionName = req.actionName;
        Map args = req.args;
        Class classByName = dirtContext.getClassByName(entityName);
        Object o2 = persistProxy.getOne(classByName, id);
        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        DirtActionType actionType = dirtEntity.getAction(actionName);
        Method method = actionType.getMethod();
        Object[] argArrays = ArgsUtil.mapToArray(objectMapper, method.getParameters(), args);
        method.invoke(o2, argArrays);
        persistProxy.save(classByName, o2);
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
        HashMap args;
    }


    @PostMapping(value = "/dirt/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建数据")
    @Transactional
    public String create(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        Class<? extends DirtBaseIdEntity> entityClass = (Class<? extends DirtBaseIdEntity>) Class.forName(entityName);
        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        body.forEach((k, v) -> {
            if ("id".equals(k)) return;
            DirtField d = dirtEntity.getDirtField((String) k);
            DirtSubmit[] dirtSubmits = d.dirtSubmit();
            if (dirtSubmits.length == 0) {
                throw new RuntimeException(k + "字段不支持创建");
            }
        });
        iID o2 = objectMapper.convertValue(body, entityClass);
        persistProxy.update(entityClass, o2, body);
        return objectMapper.writeValueAsString(Result.ok());
        }

    @PostMapping(value =  "/dirt/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新数据")
    @Transactional
    public String update(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        Class<? extends DirtBaseIdEntity> entityClass = (Class<? extends DirtBaseIdEntity>) Class.forName(entityName);

        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        body.forEach((k, v) -> {
            if ("id".equals(k)) return;
            DirtField d = dirtEntity.getDirtField((String) k);
            DirtSubmit[] dirtSubmits = d.dirtSubmit();
            if (dirtSubmits.length == 0) {
                throw new RuntimeException(k + "字段不支持更新");
            }
        });

        DirtBaseIdEntity o2 = objectMapper.convertValue(body, entityClass);
        Object one = persistProxy.getOne(entityClass, o2.getId());
        persistProxy.update(entityClass, one, body);
        return objectMapper.writeValueAsString(Result.ok());
    }

    @PostMapping(value = "/dirt/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据 id 删除")
    @Transactional
    public String deleteByid(@RequestBody DeleteByIdReq req) throws ClassNotFoundException, JsonProcessingException {
        String entityName = req.entityName;
        Long id = req.id;
        Class<?> entityClass = Class.forName(entityName);

        persistProxy.deleteById(entityClass, id);
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
        Class<?> entityClass = Class.forName(entityName);
        List<Long> ids = req.ids;
        persistProxy.deleteByIds(entityClass, ids);

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
        Class<?> entityClass = Class.forName(entityName);
        Page<Object> all = persistProxy.findAll(entityClass, reqFilter.getSpec(), pageable);
        Result success = Result.success(all);
        return objectMapper.writeValueAsString(success);
    }

    @PostMapping(value = "/dirt/getFullDatas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取全量数据")
    @Transactional(readOnly = true)
    public String fullData(@RequestBody DirtQueryFilter reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException, JsonProcessingException {
        Class<?> entityClass = Class.forName(entityName);
        List<Object> all = persistProxy.findAll(entityClass, reqFilter.getSpec());
        Result<List<Object>> success = Result.success(all);
        return objectMapper.writeValueAsString(success);

    }


    @GetMapping(value = "/dirt/getData", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取单条数据")
    @Transactional(readOnly = true)
    @SneakyThrows
    public String getById(@RequestParam(name = "entityName") String entityName, @RequestParam(name = "id") Long id)  {
        Class<?> entityClass = Class.forName(entityName);
        Object one = persistProxy.findById(entityClass, id);
        Object o = ((Optional) one).get();
        Result<Object> success = Result.success(o);
        return objectMapper.writeValueAsString(success);

    }

    @GetMapping(value = "/dirt/getEntitySchema", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取标准 schema")
    public String getTableHeaders(@RequestParam(name = "entityName") String entityName) throws  JsonProcessingException {
        DirtEntityType bySimpleName = dirtContext.getDirtEntity(entityName);
        List<DirtFieldType> heads = bySimpleName.getHeads();
        Result<List<DirtFieldType>> success = Result.success(heads);
        return objectMapper.writeValueAsString(success);

    }


    @GetMapping(value = "/dirt/getTablesNames", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取表名")
    public String getTablesNames() throws JsonProcessingException {
        Set<String> allEntityNames = dirtContext.getAllEntityNames();
        Result<Set<String>> success = Result.success(allEntityNames);
        return objectMapper.writeValueAsString(success);
    }


    @GetMapping(value = "/dirt/getTableMaps", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取所有表信息")
    public String getTableMaps() throws JsonProcessingException {
        Map<String, DirtViewType> nameEntityMap = dirtContext.getNameEntityMap();
        Result<Map<String, DirtViewType>> success = Result.success(nameEntityMap);
        return objectMapper.writeValueAsString(success);
    }

    @PostMapping(value = "/dirt/getDirtFieldType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取 DirtFieldType")
    public String getDirtField(@RequestBody GetDirtFieldReq req ) throws JsonProcessingException {
        DirtFieldType fieldType = dirtContext.getDirtEntity(req.entityName).getFieldType(req.fieldName, req.args);
        Result<DirtFieldType> success = Result.success(fieldType);
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
}
