package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.Option;
import com.zk.dirt.intef.iDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DependsProvider implements iDataSource<String,Option> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public List<Option> getSource(String dependsEnityName,String dependsFiledName, Map<String,Object> args) {
        //String  tableName = datasource.onEntity()[0].getSimpleName();
        //String column =datasource.dependsColumn();
        Query query = entityManager.createQuery("select DISTINCT "+ dependsFiledName +" from  "+dependsEnityName);
        List<String> resultList = query.getResultList();
        List<Option> collect = resultList.stream().map(s -> new Option(s, s)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String initialValue() {
        return "";
    }
}