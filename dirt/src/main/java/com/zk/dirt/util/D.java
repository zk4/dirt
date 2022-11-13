package com.zk.dirt.util;

public class D {
    public static <T> T getBean(Class<T> clazz) {
        return SpringUtil.getBean(clazz);
    }

}