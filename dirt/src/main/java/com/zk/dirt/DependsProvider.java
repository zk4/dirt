package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.intef.iDependProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DependsProvider implements iDependProvider<String, DirtEnumValue> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public Map<String, DirtEnumValue> getSource(String name) {
        HashMap source = new HashMap<Long, DirtEnumValue>();

        List<String> columns = dirtContext.getColumns(name);

        for (String column : columns) {
            source.put(column,
                    new DirtEnumValue(column, column, ""));
        }
        return source;
    }

    @Override
    public String initialValue() {
        return "";
    }
}