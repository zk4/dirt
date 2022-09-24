package com.zk.dirt.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.config.rest.DoNotWrapperResult;
import com.zk.config.rest.Result;
import com.zk.config.rest.rsql.QueryFilter2;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.*;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.util.ArgsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping("/dirt/action")
    @ApiOperation(value = "动作")
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
    public Result create(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> entityClass = Class.forName( entityName);
        Object o2 = objectMapper.convertValue(body, entityClass);
        persistProxy.save(entityClass,o2);
        return Result.ok();
    }

    @PostMapping("/dirt/update")
    @ApiOperation(value = "更新")
    @Transactional
    public Result update(@RequestParam(name = "entityName") String entityName, @RequestBody HashMap body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Class<? extends DirtBaseIdEntity> entityClass = (Class<? extends DirtBaseIdEntity>) Class.forName( entityName);
        SimpleJpaRepository jpaRepository = dirtContext.getRepo(entityName);

        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        body.forEach((k, v) -> {
            if("id".equals(k)) return;
            DirtField d = dirtEntity.getDirtField((String)k);
            if(d.dirtSubmit()==null || d.dirtSubmit().length==0){
                throw new RuntimeException(k+"字段不支持更新");
            }
        });

        DirtBaseIdEntity o2 = objectMapper.convertValue(body, entityClass);
        if (o2.getId() == null) throw new RuntimeException("没有 id，无法更新");

        Object one = persistProxy.getOne(entityClass,o2.getId());

        persistProxy.update(entityClass,one,body);
        //// 不需要传入，直接忽略
        //String[] ignoreList = {"createdTime", "updatedTime", "deleted"};
        //BeanUtils.copyProperties(o2, one, ignoreList);

        //  检测manytoone id 是否存在
        //DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityClass.getName());

        //Map<String, Class> idOfEntityMap = dirtEntity.getIdOfEntityMap();
        //Iterator<Map.Entry<String, Class>> iterator = idOfEntityMap.entrySet().iterator();
        //while (iterator.hasNext()) {
        //    Map.Entry<String, Class> next = iterator.next();
        //    String key = next.getKey();
        //
        //    if (body.get(key) == null) continue;
        //
        //    Class targetClass = next.getValue();
        //    Object o1 = objectMapper.convertValue(body.get(key), targetClass);
        //    BaseIdEntity2 o = (BaseIdEntity2) body.get(key);
        //    Long lid =o.getId();
        //    Optional byId = persistProxy.findById(next.getValue(),lid);
        //    if (!byId.isPresent())
        //        throw new RuntimeException(dirtEntity.getDirtField(key).title() + ":" + lid + " 数据不存在");
        //}
        jpaRepository.save(one);
        return Result.ok();

    }

    @PostMapping("/dirt/deleteById")
    @ApiOperation(value = "根据 id 删除")
    @Transactional
    public Result deleteByid(@RequestBody DeleteByIdReq req) throws ClassNotFoundException {
        String entityName = req.entityName;
        Long id = req.id;
        Class<?> entityClass = Class.forName( entityName);

        persistProxy.deleteById(entityClass,id);
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
    @ApiOperation(value = "根据 ids 删除")
    @Transactional
    public Result deleteByids(@RequestBody DeleteByidsReq req) throws ClassNotFoundException {
        String entityName = req.entityName;
        Class<?> entityClass = Class.forName( entityName);
        List<Long> ids = req.ids;
        persistProxy.deleteByIds(entityClass,ids);
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
    @ApiOperation(value = "分页数据")
    @Transactional(readOnly = true)
    public Result page(@RequestBody QueryFilter2 reqFilter, @RequestParam(name = "entityName") String entityName, Pageable pageable) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName( entityName);
        Page all = persistProxy.findAll(entityClass,reqFilter.getSpec(), pageable);
        return Result.success(all);
    }

    @GetMapping("/dirt/getData")
    @ApiOperation(value = "数据")
    @Transactional(readOnly = true)
    public Result getById(@RequestParam(name = "entityName") String entityName, @RequestParam(name = "id") Long id) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName( entityName);
        Object one = persistProxy.getOne(entityClass,id);

        return Result.success(one);
    }

    @GetMapping("/dirt/getTableHeaders")
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


}
