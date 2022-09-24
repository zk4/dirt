package com.zk.dirt.intef;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 存储接口
 */
public interface iPersistProxy  {

	<ID> Optional findById(Class clazz, ID id);

	<T,ID>  T getOne(Class<T> clazz, ID id) ;

	<ID>  void deleteById(Class clazz,ID id);

	<ID>  void deleteByIds(Class clazz,List<ID> ids);

	<T> Page<T> findAll(Class clazz,@Nullable Specification<T> spec, Pageable pageable);

	<S extends T,T> S save(Class clazz,S entity);


    <T> void update(Class<?> rawType, T enhancedInstance, Map args) throws IntrospectionException, IllegalAccessException, InvocationTargetException;
}
