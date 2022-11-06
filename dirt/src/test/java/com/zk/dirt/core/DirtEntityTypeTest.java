package com.zk.dirt.core;

import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.entity.Documentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DirtEntityTypeTest {

    @Test
    void getFieldMapRecurssively() {
        HashMap<String, DirtField> out = new HashMap<>();
        Map<String, DirtField> fieldMapRecurssively = DirtEntityType.getFieldMapRecursively(out, Documentation.class);
        Assertions.assertEquals(fieldMapRecurssively.size(), 3);
    }

    @Test
    void getHeads() {

    }

    @Test
    void getFieldType() {
    }


    @Test
    void getActionRecursively() {
    }

    @Test
    void getAllFields() {
    }

    @Test
    void getFieldMapRecursively() {
    }

    @Test
    void initDirtFieldMap() {
    }

    @Test
    void getAction() {
    }

    @Test
    void getDirtField() {
    }

    @Test
    void getIdOfEntityMap() {
    }
}