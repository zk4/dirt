package com.zk.dirt.core;

import com.zk.dirt.DirtApplication;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.intef.iDenpendsWithArgsDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {DirtApplication.class})
class DirtContextTest {

    @Autowired
    DirtContext dirtContext ;



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
                // dirtcontext 还未完全初始化
                assertEquals(0,valueEnum.size());
            }
        }
        assertEquals(9,heads.size());
    }

    @Test
    void getRepoByType() {
        SimpleJpaRepository repoByType = dirtContext.getRepoByType(MetaType.class);
        assertNotNull(repoByType);
    }

    @Test
    void getRepo() {
        SimpleJpaRepository repo = dirtContext.getRepo(MetaType.class.getName());
        assertNotNull(repo);
    }

    @Test
    void getAllEntityNames() {
        Set<String> allEntityNames = dirtContext.getAllEntityNames();
        assertEquals(1, allEntityNames.size());
    }

    @Test
    void getNameEntityMap() {
        Map<String, DirtViewType> nameEntityMap = dirtContext.getNameEntityMap();

    }

    @Test
    void getOptionKey() {
        String columnName = DirtContext.getOptionKey(MetaType.class.getName(), "columnName");
        assertEquals("com.zk.dirt.entity.MetaType.columnName", columnName);
    }

    @Test
    void getOptionFunction() {
        iDenpendsWithArgsDataSource columnName = dirtContext.getOptionFunction(MetaType.class.getName(), "columnName");
        assertNotNull(columnName);
    }

    @Test
    void removeOptionFunctionKey() {
    }

    @Test
    void addOptionFunction() {
    }
}