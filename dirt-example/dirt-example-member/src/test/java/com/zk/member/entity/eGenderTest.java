package com.zk.member.entity;

import com.zk.dirt.core.eFilterOperator;
import com.zk.dirt.intef.iEnumText;
import org.junit.jupiter.api.Test;

class eGenderTest {

    @Test
    void enumration() {
        Class eGenderClass = eFilterOperator.class;
        try {
            Class<? extends iEnumText> aClass = eGenderClass.asSubclass(iEnumText.class);
            iEnumText[] enumConstants = aClass.getEnumConstants();
            for (iEnumText value : enumConstants) {

                System.out.println(value);
                System.out.println(value.getText());
            }
        }catch (Exception e ){

        }
    }
}