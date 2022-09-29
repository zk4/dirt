package com.zk.dirt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.core.DirtContext;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.util.ArgsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JPA 对数据持久化的实现
 */
@Component
public class JPAPersistProxyImp implements iPersistProxy {

	@Autowired
	DirtContext dirtContext;

	@Autowired
	EntityManager entityManager;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public <ID> Optional findById(Class clazz, ID id) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		return jpaRepository.findById(id);
	}

	@Override
	public <T, ID> T getOne(Class<T> clazz, ID id){
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		return (T) jpaRepository.getOne(id);
	}

	@Override
	public <ID> void deleteById(Class clazz, ID id) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		jpaRepository.deleteById(id);
	}

	@Override
	public <ID> void deleteByIds(Class clazz, List<ID> ids) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);

		Stream<Object> objectStream = ids.stream()
				.map(aLong -> entityManager.getReference(clazz, aLong));
		List<?> entities = objectStream
				.collect(Collectors.toList());
		jpaRepository.deleteAll(entities);

	}

	@Override
	public <T> Page<T> findAll(Class clazz, Specification<T> spec, Pageable pageable) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		return jpaRepository.findAll(spec, pageable);
	}

	@Override
	public <T> List<T> findAll(Class clazz, Specification<T> spec) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		return jpaRepository.findAll(spec);
	}

	@Override
	public <S extends T, T> S save(Class clazz, S entity) {
		SimpleJpaRepository jpaRepository = dirtContext.getRepoByType(clazz);
		return (S) jpaRepository.save(entity);
	}

	@Override
	public <T> void update(Class<?> rawType, T enhancedInstance, Map args) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		ArgsUtil.updateEntity(rawType, enhancedInstance, args, entityManager,objectMapper);
	}
}
