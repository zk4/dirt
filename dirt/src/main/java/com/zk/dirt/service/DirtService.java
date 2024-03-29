package com.zk.dirt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.annotation.DirtDataSource;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.conf.DirtQueryFilter;
import com.zk.dirt.core.*;
import com.zk.dirt.entity.iID;
import com.zk.dirt.intef.iDataSource;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.intef.iResourceUploader;
import com.zk.dirt.util.ArgsUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
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

    @Autowired(required = false) // 由包引用者自行实现
    iResourceUploader resourceUploader;


    @Transactional
    public void action(String entityName, String actionName, Long id, Map args) throws IllegalAccessException, InvocationTargetException {
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
    public void create(String entityName, Map body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
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
    public void update(String entityName, Map body) throws ClassNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
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
    public void deleteById(String entityName, Long id) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        persistProxy.deleteById(entityClass, id);
     }


    @Transactional
    public void deleteByIds(String entityName, List<Long> ids) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        persistProxy.deleteByIds(entityClass, ids);
        }


    @Transactional(readOnly = true)
    public Page<Object> page(DirtQueryFilter reqFilter, String entityName, Pageable pageable) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        return persistProxy.findAll(entityClass, reqFilter.getSpec(), pageable);
    }


    @Transactional(readOnly = true)
    public List<Object> fullData(DirtQueryFilter reqFilter, String entityName, Pageable pageable) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        return persistProxy.findAll(entityClass, reqFilter.getSpec());
    }


    @Transactional(readOnly = true)
    @SneakyThrows
    public Object getById(String entityName, Long id) {
        Class<?> entityClass = Class.forName(entityName);
        Optional byId = persistProxy.findById(entityClass, id);
        if (byId.isPresent()) return byId.get();
        throw new RuntimeException("不存在的 id");
    }


    public List<DirtFieldType> getTableHeaders(String entityName) {
        DirtEntityType bySimpleName = dirtContext.getDirtEntity(entityName);
        return bySimpleName.getHeads();
    }


    public Set<String> getTablesNames()  {
        return dirtContext.getAllEntityNames();
    }


    public Map<String, DirtViewType> getTableMaps()  {
        return dirtContext.getNameEntityMap();
    }


    public DirtFieldType getDirtField(String entityName, String fieldName) {
        return dirtContext.getDirtEntity(entityName).getFieldType(fieldName);
    }


    public List<Option> getOptions(String entityName, String fieldName, Map args) throws ClassNotFoundException, NoSuchFieldException {
        Class<?> aClass = Class.forName(entityName);
        Field field = aClass.getDeclaredField(fieldName);
        DirtField[] dirtFields = field.getDeclaredAnnotationsByType(DirtField.class);
        if (dirtFields.length == 1) {
            DirtDataSource[] depends = dirtFields[0].datasource();
            if (depends.length == 1) {
                DirtDataSource depend = depends[0];
                iDataSource optionFunction = dirtContext.getOptionFunction(entityName, fieldName);
                if (optionFunction != null) {
                    String tableName=null;
                    if(depend.onEntity().length>0){
                        tableName = depend.onEntity()[0].getSimpleName();
                    }
                    String column = depend.dependsColumn();
                    List<Option> source = optionFunction.getSource(tableName, column, args);
                    return source;
                }
            }
        }

        return null;
    }

    /**
     * 业务 code 获取
     * Code 定义：
     * 1. code 一般对外。不随数据迁移而改变。
     * 2. code 一般带有业务编码规则。不是纯数字。
     * @param entityName
     * @param code
     * @return
     * @throws ClassNotFoundException
     */
    public Object getByCode(String entityName, String code) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityName);
        Optional byId = persistProxy.findByCode(entityClass, code);
        if (byId.isPresent()) return byId.get();
        throw new RuntimeException("不存在的 code");
    }
}
