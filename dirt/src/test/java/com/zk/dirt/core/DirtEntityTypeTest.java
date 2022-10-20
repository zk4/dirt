package com.zk.dirt.core;

import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.entity.Documentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class DirtEntityTypeTest {

    @Test
    void getFieldMapRecurssively() {
        HashMap<String, DirtField> out = new HashMap<>();
        Map<String, DirtField> fieldMapRecurssively = DirtEntityType.getFieldMapRecursively(out, Documentation.class);
        Assertions.assertEquals(fieldMapRecurssively.size(), 3);
    }
}