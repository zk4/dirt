package com.zk.dirt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArgsUtil {
    public final static Object[] ZERO_OBJECT = new Object[]{};

    public static Object[] mapToArray(ObjectMapper objectMapper, Parameter[] parameters, Map map) {
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

    /**
     * 获取Field 类型为 Collection containerType, 和 里面的type
     * 如 List<Member> 将返回 com.util.List 和 com.xxx.xxx.Member 的 class 数组
     *
     * @param field
     * @return
     */
    public static Class<?>[] getCollectionItemType(Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length == 1) {
            Type actualTypeArgument = actualTypeArguments[0];
            try {
                Class innerClass = Class.forName(actualTypeArgument.getTypeName());
                Class containerClass = Class.forName(parameterizedType.getRawType().getTypeName());

                return new Class[]{containerClass, innerClass};
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        throw new RuntimeException("不是 Collection 类型");
    }

    /**
     * 给 entity enhancedInstance 的 relation 通过 id 赋值做关联关系
     * 不支持 eintity 嵌套，基本无此业务形态
     * @param enhancedInstance
     * @param args
     * @param entityManager
     * @param <T>
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> void assignRelationIds(Class rawType, T enhancedInstance, HashMap<String, ArrayList<Long>> args, EntityManager entityManager) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        // convert ids to entity reference, then set it back to managed entity
        for (Field declaredField : rawType.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToMany.class)) {
                Class[] classes = ArgsUtil.getCollectionItemType(declaredField);
                // Class contianer = classes[0];
                Class innerType = classes[1];
                String fieldName = declaredField.getName();
                ArrayList<Long> ids = args.get(fieldName);
                if (ids != null) {
                    Set<Object> collect = ids.stream()
                            .map(id -> entityManager.getReference(innerType, id))
                            .collect(Collectors.toSet());

                    // set entity reference back to oneToMany annotated field
                    Method mehtod = new PropertyDescriptor(declaredField.getName(),
                            enhancedInstance.getClass()).getWriteMethod();
                    mehtod.invoke(enhancedInstance, collect);
                }
            }
        }
    }
}
