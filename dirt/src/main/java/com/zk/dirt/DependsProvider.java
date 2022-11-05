package com.zk.dirt;

import com.zk.dirt.annotation.DirtDepends;
import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.core.Option;
import com.zk.dirt.intef.iDenpendsWithArgsDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DependsProvider implements iDenpendsWithArgsDataSource<String, DirtEnumValue> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public List<Option> getSource(DirtDepends depends, Map<String,Object> args) {
        String  tableName = depends.onEntity()[0].getSimpleName();
        String column =depends.onColumn();
        Query query = entityManager.createQuery("select DISTINCT "+ column +" from  "+tableName);
        List<String> resultList = query.getResultList();
        List<Option> collect = resultList.stream().map(s -> new Option(s, s)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String initialValue() {
        return "";
    }
}