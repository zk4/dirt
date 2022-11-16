package com.zk.dirt.core;


import com.zk.dirt.annotation.DirtDataSource;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtScan;
import com.zk.dirt.intef.iDataSource;
import com.zk.dirt.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DirtContext {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EntityManager entityManager;

    //private final static LinkedHashMap<String, DirtEntityType> nameDirtEntityMap = new LinkedHashMap<>();
    private final static LinkedHashMap<String, Supplier<DirtEntityType>> nameLambdaDirtEntityMap = new LinkedHashMap<>();
    private final static Map<String, Class> nameClassMap = new HashMap<String, Class>();
    private final static Map<String, SimpleJpaRepository> nameReposMap = new HashMap<String, SimpleJpaRepository>();
    private final static Map<Class, SimpleJpaRepository> classReposMap = new HashMap<Class, SimpleJpaRepository>();
    private final static Map<String, DirtViewType> nameEntityMap = new HashMap<String, DirtViewType>();
    private final static Map<String, List<String>> nameColumns = new HashMap<String, List<String>>();
    private static Map<String, iDataSource> dependDataSources = new HashMap<>();


    public DirtContext() {
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        String mainClassName = PackageUtil.getMainClassName();
        Class<?> aClass = Class.forName(mainClassName);
        DirtScan scanPackageAnno = aClass.getDeclaredAnnotation(DirtScan.class);
        String[] scanPackages = new String[]{"com.zk.dirt.entity"};

        // merge scan packages if found
        if (scanPackageAnno != null) {
            scanPackages = (String[]) ArrayUtils.addAll(scanPackages, scanPackageAnno.value());
        }
        log.info("@DirtEntity 扫描路径:" + Arrays.stream(scanPackages).collect(Collectors.joining(", ")));

        dependDataSources = applicationContext.getBeansOfType(iDataSource.class);
        //System.out.println(beansOfType);

        for (String packagePath : scanPackages) {
            Set<Class> classAnnotationClasses = PackageUtil.findClassAnnotationClasses(packagePath, DirtEntity.class);
            for (Class classAnnotationClass : classAnnotationClasses) {
                String simpleName = classAnnotationClass.getName();
                if (nameLambdaDirtEntityMap.get(simpleName) != null) {
                    throw new RuntimeException("重复的 DirtEntity " + simpleName);
                }

                //nameDirtEntityMap.put(simpleName, new DirtEntityType(this, applicationContext, classAnnotationClass));
                nameLambdaDirtEntityMap.put(simpleName, () -> new DirtEntityType(this, applicationContext, classAnnotationClass));

                nameClassMap.put(simpleName, classAnnotationClass);

                if (classAnnotationClass.getAnnotation(Entity.class) != null) {
                    SimpleJpaRepository jpaRepository = new SimpleJpaRepository(classAnnotationClass, entityManager);
                    nameReposMap.put(simpleName, jpaRepository);
                    classReposMap.put(classAnnotationClass, jpaRepository);
                }

                DirtEntity declaredAnnotation = (DirtEntity) classAnnotationClass.getDeclaredAnnotation(DirtEntity.class);
                if (declaredAnnotation.visiable()) {
                    DirtViewType dirtViewType = new DirtViewType();
                    dirtViewType.setText(declaredAnnotation.value());
                    dirtViewType.setClassName(simpleName);
                    dirtViewType.setViewType(declaredAnnotation.viewType());
                    nameEntityMap.put(simpleName, dirtViewType);
                }
                // 表对应 columns
                ArrayList<String> columns = new ArrayList<>();
                Field[] declaredFields = classAnnotationClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    columns.add(declaredField.getName());
                }
                nameColumns.put(simpleName, columns);
            }
        }
       //log.info(nameDirtEntityMap.toString());
    }

    public List<String>  getColumns(String className){
        return nameColumns.get(className);
    }

    public Class getClassByName(String name) {
        Class aClass = nameClassMap.get(name);
        ;
        if (aClass == null) {
            throw new RuntimeException("不存在 " + aClass);
        }
        return aClass;
    }

    public DirtEntityType getDirtEntity(String name) {
        Supplier<DirtEntityType> dirtEntityTypeSupplier = nameLambdaDirtEntityMap.get(name);
        if(dirtEntityTypeSupplier!=null) {
            DirtEntityType dirtEntity = dirtEntityTypeSupplier.get();
            if (dirtEntity == null) {
                throw new RuntimeException("不存在 " + name);
            }
            return dirtEntity;
        }
        return null;
    }

    public SimpleJpaRepository getRepoByType(Class type) {
        SimpleJpaRepository simpleJpaRepository = classReposMap.get(type);
        if (simpleJpaRepository == null) {
            throw new RuntimeException("不存在 simpleJpaRepository" + classReposMap);
        }
        return simpleJpaRepository;
    }

    public SimpleJpaRepository getRepo(String name) {
        SimpleJpaRepository simpleJpaRepository = nameReposMap.get(name);
        if (simpleJpaRepository == null) {
            throw new RuntimeException("不存在 simpleJpaRepository" + name);
        }
        return simpleJpaRepository;
    }

    public Set<String> getAllEntityNames() {
        return nameReposMap.keySet();
    }

    public Map<String, DirtViewType> getNameEntityMap() {
        return nameEntityMap;
    }

    static public String getOptionKey(String entityName, String subKey){
        return entityName+"."+subKey;
    }

    public iDataSource getOptionFunction(String entityName, String subKey){
        //String optionKey = getOptionKey(entityName, subKey);
        try {
            Field field = Class.forName(entityName).getDeclaredField(subKey);
            DirtField declaredAnnotation = field.getDeclaredAnnotation(DirtField.class);
            if(declaredAnnotation!=null) {
                DirtDataSource[] depends = declaredAnnotation.datasource();
                if(depends.length>0){
                    DirtDataSource depend = depends[0];
                    Class<? extends iDataSource> aClass = depend.value();
                    return applicationContext.getBean(aClass);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //public void removeOptionFunctionKey(String entityName,String subKey) {
    //    String optionKey = getOptionKey(entityName, subKey);
    //    dependDataSources.remove(optionKey);
    //}
    //public void addOptionFunction(String key, iDataSource denpendsWithArgsDataSource){
    //    dependDataSources.put(key, denpendsWithArgsDataSource);
    //}
}
