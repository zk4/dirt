package com.zk.dirt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.entity.DirtBaseIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Parameter;
import java.lang.reflect.*;
import java.util.Collection;
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
     * 注意此方法，不校验 id 是否存在，即使不存在，也会拿到 id reference
     *
     * @param rawType          未被 proxy 的 entity 类型
     * @param enhancedInstance 已被 proxy 的 entity 类型实例
     * @param args             entity hashmap 形式，OneToMany 与 ManyToOne 均以 id List 或 Set 形式存在
     * @param entityManager
     * @param <T>              已被 proxy 的 entity 类型
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> void updateEntity(Class<?> rawType, T enhancedInstance, Map args, EntityManager entityManager, ObjectMapper objectMapper) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        // types is converted, save me sometime
        Object typedArgs = objectMapper.convertValue(args, rawType);

        for (Field declaredField : rawType.getDeclaredFields()) {
            String fieldName = declaredField.getName();

            Method getter = new PropertyDescriptor(fieldName, rawType).getReadMethod();

            Object arg = getter.invoke(typedArgs);
            if (arg == null) continue;

            if (declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToMany.class)) {
                Class[] classes = ArgsUtil.getCollectionItemType(declaredField);

                Class containerType = classes[0];
                Boolean isSet = containerType.isAssignableFrom(Set.class);
                // 容器内部类型
                Class innerType = classes[1];

                Collection idObjs = (Collection) arg;

                // 根据 id 获取 entity reference
                arg = idObjs.stream()
                        .map(idObj -> {
                            return entityManager.getReference(innerType, ((DirtBaseIdEntity) idObj).getId());
                        })
                        .collect(isSet ? Collectors.toSet() : Collectors.toList());

            } else if (declaredField.isAnnotationPresent(ManyToOne.class) || declaredField.isAnnotationPresent(OneToOne.class)) {
                Class<?> type = declaredField.getType();
                arg = entityManager.getReference(type, ((DirtBaseIdEntity) arg).getId());
            } else {
                // primitive field

            }

            // set entity reference back to annotated field
            Method setter = new PropertyDescriptor(fieldName, enhancedInstance.getClass()).getWriteMethod();
            System.out.println(fieldName);
            setter.invoke(enhancedInstance, arg);
        }
    }


    // https://gist.github.com/bmchild/2343316
    @SuppressWarnings("rawtypes")
    public static Class<?> getEntity(JpaRepository repo) {
        Type clazzes = getGenericType(repo.getClass())[0];
        Type[] jpaClass = getGenericType(getClass(clazzes));
        return getClass(((ParameterizedType) jpaClass[0]).getActualTypeArguments()[0]);
    }

    public static Type[] getGenericType(Class<?> target) {
        if (target == null)
            return new Type[0];
        Type[] types = target.getGenericInterfaces();
        if (types.length > 0) {
            return types;
        }
        Type type = target.getGenericSuperclass();
        if (type != null) {
            if (type instanceof ParameterizedType) {
                return new Type[]{type};
            }
        }
        return new Type[0];
    }

    /*
     * Get the underlying class for a type, or null if the type is a variable
     * type.
     *
     * @param type
     * @return the underlying class
     */
    @SuppressWarnings("rawtypes")
    private static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
