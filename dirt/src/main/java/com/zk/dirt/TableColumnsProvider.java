package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.Option;
import com.zk.dirt.intef.iDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TableColumnsProvider implements iDataSource<String,Option > {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public List<Option> getSource(String dependsEnityName,String dependsFiledName,  Map<String,Object> args) {
        String  tableName = (String)args.get(dependsFiledName);
        List<Option> collect = dirtContext.getColumns(tableName).stream().map(s -> new Option(s, s)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String initialValue() {
        return "";
    }
}