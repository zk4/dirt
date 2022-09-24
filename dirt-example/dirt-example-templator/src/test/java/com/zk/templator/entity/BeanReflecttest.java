package com.zk.templator.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanReflecttest {

    @Test
    void getGetter() throws IntrospectionException {
        Method items = new PropertyDescriptor("items", Templator.class).getWriteMethod();
        Assertions.assertEquals(items.getName(), "setItems");
    }
}
