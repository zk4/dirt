package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.Option;
import com.zk.dirt.intef.iDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MetaTableProvider implements iDataSource<String, Option> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;

    //public Map<String, DirtEnumValue> getSource() {
    //
    //}

    @Override
    public List<Option> getSource(String dependsEnityName, String dependsFiledName, Map<String, Object> args) {
        //HashMap source = new HashMap<Long, DirtEnumValue>();
        Set<String> allEntityNames = dirtContext.getAllEntityNames();
        List<Option> collect = allEntityNames.stream().map(s -> new Option(s, s)).collect(Collectors.toList());

        //for (String value : allEntityNames) {
        //    source.put(value,
        //            new DirtEnumValue(value, value, ""));
        //}
        return collect;
    }

    @Override
    public String initialValue() {
        return "";
    }
}