package com.zk.dirt.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.config.rest.CodeMsg;
import com.zk.config.rest.DoNotWrapperResult;
import com.zk.config.rest.Result;
import com.zk.config.rest.rsql.QueryFilter2;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.*;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.intef.iResourceUploader;
import com.zk.dirt.util.ArgsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (resourceUploader == null) {
            throw new RuntimeException("没实现 iResourceUploader, 无法上传");
        }
        return  resourceUploader.store(file);
    }


    @PostMapping("/dirt/action")
    @ApiOperation(value = "执行动作")
    @Transactional
    public Result action(@RequestBody ActionReq req) throws IllegalAccessException, InvocationTargetException {
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
        return Result.ok();
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


    @PostMapping("/dirt/create")
    @ApiOperation(value = "创建")
    @Transactional
    public Result create(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
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
        DirtBaseIdEntity o2 = objectMapper.convertValue(body, entityClass);
        persistProxy.update(entityClass, o2, body);
        return Result.ok();
    }

    @PostMapping("/dirt/update")
    @ApiOperation(value = "更新")
    @Transactional
    public Result update(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
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
        return Result.ok();

    }

    @PostMapping("/dirt/deleteById")
    @ApiOperation(value = "根据 id 删除")
    @Transactional
    public Result deleteByid(@RequestBody DeleteByIdReq req) throws ClassNotFoundException {
        String entityName = req.entityName;
        Long id = req.id;
        Class<?> entityClass = Class.forName(entityName);

        persistProxy.deleteById(entityClass, id);
        return Result.ok();
    }

    @Data
    @ApiModel("DeleteByIdReq")
    static class DeleteByIdReq {
        @ApiModelProperty("实体名")
        String entityName;
        @ApiModelProperty("id")
        Long id;
    }

    @PostMapping("/dirt/deleteByIds")
    @ApiOperation(value = "根据 ids 批量删除")
    @Transactional
    public Result deleteByids(@RequestBody DeleteByidsReq req) throws ClassNotFoundException {
        String entityName = req.entityName;
        Class<?> entityClass = Class.forName(entityName);
        List<Long> ids = req.ids;
        persistProxy.deleteByIds(entityClass, ids);
        return Result.ok();
    }

    @Data
    @ApiModel("DeleteByidsReq")
    static class DeleteByidsReq {
        @ApiModelProperty("实体名")
        String entityName;
        @ApiModelProperty("ids")
        List<Long> ids;
    }

    @PostMapping("/dirt/getDatas")
    @ApiOperation(value = "获取分页数据")
    @Transactional(readOnly = true)
    public Result page(@RequestBody QueryFilter2 reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        Page all = persistProxy.findAll(entityClass, reqFilter.getSpec(), pageable);
        return Result.success(all);
    }

    @PostMapping("/dirt/getFullDatas")
    @ApiOperation(value = "获取全量数据")
    @Transactional(readOnly = true)
    public Result fullData(@RequestBody QueryFilter2 reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        List<Object> all = persistProxy.findAll(entityClass, reqFilter.getSpec());
        return Result.success(all);
    }


    @GetMapping("/dirt/getData")
    @ApiOperation(value = "获取单条数据")
    @Transactional(readOnly = true)
    @SneakyThrows
    public Result getById(@RequestParam(name = "entityName") String entityName, @RequestParam(name = "id") Long id) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        Object one = persistProxy.findById(entityClass, id);
        if (((Optional) one).isPresent()) {
            return Result.success(((Optional) one).get());
        } else {
            return Result.error(CodeMsg.ENTITY_ID_NOT_EXIST_ERROR);
        }

    }

    @GetMapping("/dirt/getEntitySchema")
    @ApiOperation(value = "获取标准 schema")
    @DoNotWrapperResult
    public Result getTableHeaders(@RequestParam(name = "entityName") String entityName) throws ClassNotFoundException {
        DirtEntityType bySimpleName = dirtContext.getDirtEntity(entityName);
        List<DirtFieldType> heads = bySimpleName.getHeads();
        return Result.success(heads);
    }


    @GetMapping("/dirt/getTablesNames")
    public Set<String> getTablesNames() {
        return dirtContext.getAllEntityNames();
    }


    @GetMapping("/dirt/getTableMaps")
    public Map<String, DirtViewType> getTableMaps() {
        return dirtContext.getNameEntityMap();
    }

    @GetMapping("/dirt/getDepends")
    public Object getDepends(@RequestParam(name = "entityName") String entityName,@RequestParam(name = "depends") String depends) {

    }



}
