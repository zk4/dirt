package com.zk.dirt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;
import java.util.Map;

public class ArgsUtil {
    public final static Object[] ZERO_OBJECT = new Object[]{};

    public static Object[] mapToArray(ObjectMapper objectMapper,Parameter[] parameters, Map map) {
        if (parameters == null) {
            throw new RuntimeException("parameters can not be null");
        }
        if (map == null) {
            return ZERO_OBJECT;
        }
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();
            Object o = map.get(parameter.getName());
            args[i] = objectMapper.convertValue(o, type);
        }

        return args;
    }
}
