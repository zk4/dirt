package com.zk.dirt.core;

import com.zk.dirt.DirtApplication;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.intef.iDenpendsWithArgsDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {DirtApplication.class})
class DirtContextTest {

    @Autowired
    DirtContext dirtContext;


    @Test
    void check0() {
        assertNotNull(dirtContext);
    }

    @Test
    void getColumns() {
        iDenpendsWithArgsDataSource ds = dirtContext.getOptionFunction(MetaType.class.getName(), "columnName");
        assertNotNull(ds);
        HashMap<String, Object> args = new HashMap<>();
        args.put("tableName", MetaType.class.getName());
        List tableName = ds.getSource(MetaType.class.getName(), "tableName", args);
        System.out.println(tableName);
        assertEquals(7, tableName.size());
    }

    @Test
    void getClassByName() {
        Class classByName = dirtContext.getClassByName(MetaType.class.getName());
        System.out.println(classByName);
        assertEquals(MetaType.class, classByName);
    }

    @Test
    void getDirtEntity() {
        DirtEntityType dirtEntity = dirtContext.getDirtEntity(MetaType.class.getName());
        System.out.println(dirtEntity);
        List<DirtFieldType> heads = dirtEntity.getHeads();
        for (DirtFieldType head : heads) {
            if(head.getTitle().equals("实体全名")){
                Map valueEnum = head.getValueEnum();
                assertEquals(valueEnum.size(), 1);
            }
        }
        System.out.println(heads);
    }

    @Test
    void getRepoByType() {
    }

    @Test
    void getRepo() {
    }

    @Test
    void getAllEntityNames() {
    }

    @Test
    void getNameEntityMap() {
    }

    @Test
    void getOptionKey() {
    }

    @Test
    void getOptionFunction() {
    }

    @Test
    void removeOptionFunctionKey() {
    }

    @Test
    void addOptionFunction() {
    }
}