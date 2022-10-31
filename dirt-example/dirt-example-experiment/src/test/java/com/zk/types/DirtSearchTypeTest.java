package com.zk.types;

import com.zk.dirt.core.DirtFieldType;
import com.zk.dirt.core.DirtSearchType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DirtSearchTypeTest {

    @Test
    void name() {
        DirtFieldType dirtFieldType = new DirtFieldType(null);
        HashMap<String,String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("hello", "hell");
        dirtFieldType.setValueEnum(objectObjectHashMap);
        DirtSearchType dirtSearchType = new DirtSearchType(dirtFieldType,null);
        System.out.println(dirtSearchType.getValueEnum());
    }
}
