package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.intef.iEnumProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MetaTableProvider implements iEnumProvider<String, DirtEnumValue> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public Map<String, DirtEnumValue> getSource() {
        HashMap source = new HashMap<Long, DirtEnumValue>();
        Set<String> allEntityNames = dirtContext.getAllEntityNames();

        for (String value : allEntityNames) {
            source.put(value,
                    new DirtEnumValue(value, value, ""));
        }
        return source;
    }

    @Override
    public String initialValue() {
        return "";
    }
}