package com.zk.entity;

import com.zk.dirt.core.eFilterOperator;
import com.zk.dirt.intef.iListable;
import org.junit.jupiter.api.Test;

class eGenderTest {

    @Test
    void enumration() {
        Class eGenderClass = eFilterOperator.class;
        try {
            Class<? extends iListable> aClass = eGenderClass.asSubclass(iListable.class);
            iListable[] enumConstants = aClass.getEnumConstants();
            for (iListable value : enumConstants) {

                System.out.println(value);
                System.out.println(value.getText());
            }
        }catch (Exception e ){

        }
    }
}