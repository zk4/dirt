package com.zk.dirt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.conf.DirtQueryFilter;
import com.zk.dirt.core.*;
import com.zk.dirt.entity.iID;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.intef.iResourceUploader;
import com.zk.dirt.util.ArgsUtil;
import com.zk.dirt.wrapper.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
public class DirtService {

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


    @Transactional
    public void action( String entityName, String actionName,  Long id, Map args) throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        Class classByName = dirtContext.getClassByName(entityName);
        Object o2 = persistProxy.getOne(classByName, id);
        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        DirtActionType actionType = dirtEntity.getAction(actionName);
        Method method = actionType.getMethod();
        Object[] argArrays = ArgsUtil.mapToArray(objectMapper, method.getParameters(), args);
        method.invoke(o2, argArrays);
        persistProxy.save(classByName, o2);
    }




    @Transactional
    public void create(  String entityName, Map body) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        Class<? extends iID> entityClass = (Class<? extends iID>) Class.forName(entityName);
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

    }


    @Transactional
    public void update( String entityName,   Map body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException, JsonProcessingException {
        Class<? extends iID> entityClass = (Class<? extends iID>) Class.forName(entityName);

        DirtEntityType dirtEntity = dirtContext.getDirtEntity(entityName);
        body.forEach((k, v) -> {
            if ("id".equals(k)) return;
            DirtField d = dirtEntity.getDirtField((String) k);
            DirtSubmit[] dirtSubmits = d.dirtSubmit();
            if (dirtSubmits.length == 0) {
                throw new RuntimeException(k + "字段不支持更新");
            }
        });

        iID o2 = objectMapper.convertValue(body, entityClass);
        Object one = persistProxy.getOne(entityClass, o2.getId());
        persistProxy.update(entityClass, one, body);
    }


    @Transactional
    public String deleteById(String entityName, Long id) throws ClassNotFoundException, JsonProcessingException {
        Class<?> entityClass = Class.forName(entityName);
        persistProxy.deleteById(entityClass, id);
        return objectMapper.writeValueAsString(Result.ok());
    }


    @Transactional
    public String deleteByIds(String entityName, List<Long> ids) throws ClassNotFoundException, JsonProcessingException {
        Class<?> entityClass = Class.forName(entityName);
        persistProxy.deleteByIds(entityClass, ids);
        return objectMapper.writeValueAsString(Result.ok());

    }


    @Transactional(readOnly = true)
    public Page<Object> page(DirtQueryFilter reqFilter, String entityName, Pageable pageable) throws ClassNotFoundException, JsonProcessingException {
        Class<?> entityClass = Class.forName(entityName);
        return persistProxy.findAll(entityClass, reqFilter.getSpec(), pageable);
    }


    @Transactional(readOnly = true)
    public List<Object> fullData(DirtQueryFilter reqFilter, String entityName, Pageable pageable) throws ClassNotFoundException, JsonProcessingException {
        Class<?> entityClass = Class.forName(entityName);
        return persistProxy.findAll(entityClass, reqFilter.getSpec());
    }


    @Transactional(readOnly = true)
    @SneakyThrows
    public Object getById(String entityName, Long id) {
        Class<?> entityClass = Class.forName(entityName);
        Optional byId = persistProxy.findById(entityClass, id);
        if(byId.isPresent())return byId.get();
        throw new RuntimeException("不存在的 id");
    }


    public List<DirtFieldType> getTableHeaders(String entityName) throws JsonProcessingException {
        DirtEntityType bySimpleName = dirtContext.getDirtEntity(entityName);
        return bySimpleName.getHeads();
    }


    public Set<String> getTablesNames() throws JsonProcessingException {
        return dirtContext.getAllEntityNames();
    }


    public Map<String, DirtViewType> getTableMaps() throws JsonProcessingException {
        return dirtContext.getNameEntityMap();
    }


    public DirtFieldType getDirtField(String entityName, String fieldName, Map args) throws JsonProcessingException {
        return  dirtContext.getDirtEntity(entityName).getFieldType(fieldName, args);
    }

    public Object getByCode(String entityName, String code) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        Optional byId = persistProxy.findByCode(entityClass, code);
        if(byId.isPresent())return byId.get();
        throw new RuntimeException("不存在的 code");
    }
}
