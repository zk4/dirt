package com.zk.dirt.core;


import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.util.PackageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class DirtContext {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EntityManager entityManager;

    private final static LinkedHashMap<String, DirtEntityType> nameDirtEntityMap = new LinkedHashMap<>();
    private final static Map<String,Class> nameClassMap = new HashMap<String,Class>();
    private final static Map<String, SimpleJpaRepository> nameReposMap = new HashMap<String,SimpleJpaRepository>();
    private final static Map<Class, SimpleJpaRepository> classReposMap = new HashMap<Class,SimpleJpaRepository>();
    private final static Map<String, String> nameEntityMap = new HashMap<String,String>();


    public DirtContext() {

    }


    @PostConstruct
    public void init(){
        // TODO: dynamic config dirtEntity scan path
        Set<Class> classAnnotationClasses = PackageUtil.findClassAnnotationClasses("com.zk", DirtEntity.class);
        for (Class classAnnotationClass : classAnnotationClasses) {
            String simpleName = classAnnotationClass.getName();
            if(nameDirtEntityMap.get(simpleName)!=null){
                throw new RuntimeException("重复的 DirtEntity "+simpleName);
            }
            nameDirtEntityMap.put(simpleName, new DirtEntityType(this,applicationContext,classAnnotationClass));
            nameClassMap.put(simpleName,classAnnotationClass);

            if(classAnnotationClass.getAnnotation(Entity.class)!=null) {
                SimpleJpaRepository jpaRepository = new SimpleJpaRepository(classAnnotationClass, entityManager);
                nameReposMap.put(simpleName, jpaRepository);
                classReposMap.put(classAnnotationClass,jpaRepository);

                 DirtEntity declaredAnnotation = (DirtEntity) classAnnotationClass.getDeclaredAnnotation(DirtEntity.class);

                 if(declaredAnnotation.visiable())
                    nameEntityMap.put(simpleName,declaredAnnotation.value());
            }
        }
        System.out.println(nameDirtEntityMap);
    }


    public Class getClassByName(String name){
        Class aClass = nameClassMap.get(name);;
        if(aClass==null) {
            throw new RuntimeException("不存在 " + aClass);
        }
        return aClass;
    }
    public DirtEntityType getDirtEntity(String name){
        DirtEntityType dirtEntity = nameDirtEntityMap.get(name);
        if(dirtEntity==null) {
            System.out.println(nameDirtEntityMap);
            throw new RuntimeException("不存在 " + name);
        }
        return dirtEntity;
    }
    public  SimpleJpaRepository getRepoByType(Class type){
        SimpleJpaRepository simpleJpaRepository = classReposMap.get(type);
        if(simpleJpaRepository==null) {
            throw new RuntimeException("不存在 simpleJpaRepository" + classReposMap);
        }
        return simpleJpaRepository;
    }
    public  SimpleJpaRepository getRepo(String name){
        SimpleJpaRepository simpleJpaRepository = nameReposMap.get(name);
        if(simpleJpaRepository==null) {
            throw new RuntimeException("不存在 simpleJpaRepository" + name);
        }
        return simpleJpaRepository;
    }
    public Set<String>  getAllEntityNames(){
        return nameReposMap.keySet();
    }

    public   Map<String, String> getNameEntityMap() {
        return nameEntityMap;
    }
}
