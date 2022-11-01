package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.intef.iWithArgDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TableColumnsProvider implements iWithArgDataSource<String, DirtEnumValue> {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;
    @Override
    public Map<String, DirtEnumValue> getSource(Map<String,Object> args) {
        HashMap source = new HashMap<Long, DirtEnumValue>();
        String  tableName = (String)args.get("tableName");
        //TODO: 仅获取 metaable = true
        List<String> columns = dirtContext.getColumns(tableName);

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