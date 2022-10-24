package com.zk.dirt.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirtSubmitTypeTest {

    @Test
    @DisplayName(value = "submit valueType 不设值时，与 field 保持一致")
    void getValueType() {
        DirtFieldType dirtFieldType = new DirtFieldType(null);
        dirtFieldType.setValueType(eUIType.time.toString());
        DirtSubmitType dirtSubmitType = new DirtSubmitType(dirtFieldType,null);
        Assertions.assertEquals(dirtSubmitType.getValueType(),"time");
    }

    @Test
    @DisplayName(value = "submit valueType 设值时，用 submit 自己的")
    void getValueType2() {
        DirtFieldType dirtFieldType = new DirtFieldType(null);
        dirtFieldType.setValueType(eUIType.time.toString());
        DirtSubmitType dirtSubmitType = new DirtSubmitType(dirtFieldType,null);
        dirtSubmitType.setValueType(eUIType.textarea.toString());
        Assertions.assertEquals(dirtSubmitType.getValueType(),"textarea");
    }

}