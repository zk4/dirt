package com.zk.dirt.core;

import com.zk.dirt.annotation.*;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.experiment.ColProps;
import com.zk.dirt.intef.iEnumProvider;
import com.zk.dirt.intef.iListable;
import lombok.Getter;
import org.springframework.context.ApplicationContext;

import javax.persistence.*;
import java.lang.reflect.Parameter;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * describe the meta data of types managed by Dirt
 * it's a mirroed structrue corssponding to  entity
 * include：
 * 1. member field (states)
 * 2. acitons      (functions)
 */
public class DirtEntityType {

    private Class<?> entityClass;

    private final Map<String, DirtField> dirtFieldMap = new HashMap<>();

    private final Map<String, DirtActionType> actionMap = new HashMap<>();

    private List<DirtFieldType> heads = new ArrayList<>();
    @Getter
    private final Map<String, Class> idOfEntityMap = new HashMap<>();

    ApplicationContext applicationContext;

    DirtContext dirtContext;

    Boolean inited = false;

    public DirtEntityType(DirtContext context, ApplicationContext applicationContext, Class classAnnotationClass) {
        this.dirtContext = context;
        this.applicationContext = applicationContext;
        this.entityClass = classAnnotationClass;

    }


    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public List<DirtFieldType> getHeads() {
        // TODO: 可静态的应该尽量静态化
        if (!inited) {
            initDirtFieldMap();
            initIdOfEntityMap();
            initActionMap();
            initHeads();
            inited = true;
        }
        return heads;
    }

    private void initHeads() {

        List<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, entityClass);
        // TODO:
        // 统一 field， method 基于 DirtField 的构造
        // 实现类似 vue 里 computed 的效果
        this.heads = fields.stream()
                .filter(field -> field.getDeclaredAnnotation(DirtField.class) != null)
                .map(field -> {
                    DirtFieldType tableHeader = new DirtFieldType();
                    DirtField dirtField = field.getDeclaredAnnotation(DirtField.class);
                    if(dirtField.title().length()==0){
                        tableHeader.setTitle(field.getName());
                    }else {
                        tableHeader.setTitle(dirtField.title());
                    }
                    tableHeader.setIndex(dirtField.index());
                    //tableHeader.setWidth(dirtField.width());
                    tableHeader.setFixed(dirtField.fixed());
                    tableHeader.setEllipsis(dirtField.ellipsis());
                    tableHeader.setCopyable(dirtField.copyable());
                    tableHeader.setSearch(dirtField.search());
                    tableHeader.setOnFilter(dirtField.onFilter());
                    tableHeader.setFilters(dirtField.filters());
                    tableHeader.setHideInTable(dirtField.hideInTable());
                    tableHeader.setSorter(dirtField.sorter());
                    eUIType uiType = dirtField.uiType();
                    Class<? extends DirtBaseIdEntity>[] classes = dirtField.idOfEntity();
                    Class<?> fieldRetType = field.getType();
                    if (classes.length > 0) {
                        Class<? extends DirtBaseIdEntity> entityClass = classes[0];
                        String simpleName = entityClass.getName();
                        tableHeader.setIdOfEntity(simpleName);
                    }else {
                        //TODO: deduce Entity Type from Return Type if there is relations
                        // 1 is from BaseEntity2
                        // 2 is element container, like list<>  set<>,  maby map<?,?>



                    }
                    String uiTypeStr = uiType.toString();

                    // set uiType  if value
                    if (uiTypeStr!=null && uiTypeStr.equals("auto")) {
                        // 设置  uiType by rettype if uiType is not set
                        Class<?> type = fieldRetType;
                        // TODO: 如果 idOfEntity 不为 null，则不自动生成 uiType? 但好像也可以生成
                        if (tableHeader.idOfEntity==null) {
                            if (type.isAssignableFrom(LocalDateTime.class)) uiTypeStr = "dateTime";
                            else if (type.isAssignableFrom(LocalDate.class)) uiTypeStr = "date";
                            else if (type.isAssignableFrom(Long.class)) uiTypeStr = "digit";
                            else if (type.isAssignableFrom(Integer.class)) uiTypeStr = "digit";
                            else if (type.isAssignableFrom(Float.class)) uiTypeStr = "digit";
                            else if (type.isAssignableFrom(Double.class)) uiTypeStr = "digit";
                            else if (type.isAssignableFrom(BigDecimal.class)) uiTypeStr = "money";
                            else if (type.isAssignableFrom(Boolean.class)) uiTypeStr = "switch";
                        }
                    }
                    tableHeader.setValueType(uiTypeStr);

                    // 设置 valueEnum, initialValue
                    Class<? extends iEnumProvider>[] providerClasses = dirtField
                            .enumProvider();
                    Map source = null;
                    Object initialValue = null;

                    if (providerClasses.length > 0) {
                        Class<? extends iEnumProvider> providerClass = providerClasses[0];
                        iEnumProvider enumProvider = applicationContext.getBean(providerClass);
                        source = enumProvider.getSource();
                        initialValue = enumProvider.initialValue();
                    } else {
                        // 如果没有提供 provider, 但又是枚举类型，且实现了 iDirtListable 接口，构造 source
                        Class<? extends iListable> listableClass = null;

                        boolean enumConstant = fieldRetType.isEnum();
                        if (enumConstant) {
                            Class enumType = fieldRetType;
                            listableClass = enumType.asSubclass(iListable.class);
                        }
                        Class<? extends iListable>[] classes1 = dirtField.enumListableType();
                        if (classes1.length > 0) {
                            listableClass = classes1[0];
                        }
                        if (listableClass != null) {

                            try {
                                iListable[] enumConstants = listableClass.getEnumConstants();
                                source = new LinkedHashMap();
                                for (iListable value : enumConstants) {
                                    if (value.toString().equals("null")) {
                                        System.out.println("");
                                    }
                                    source.put(value, new DirtEnumValue(value.getText(), value.toString(), ""));
                                }

                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                        DirtHQLSource[] dirtSources = dirtField.sourceProvider();
                        if (dirtSources.length > 0) {
                            DirtHQLSource dirtSource = dirtSources[0];
                            String hql = dirtSource.hql();
                            Query query = applicationContext.getBean(EntityManager.class).createQuery(hql);
                            List options = query.getResultList();
                            source = new LinkedHashMap();
                            for (Object option : options) {
                                Map option1 = (Map) option;
                                // TODO: 不可这样写，需要与实现无关
                                source.put(option1.get("status"),
                                        new DirtEnumValue(option1.get("text"),
                                                option1.get("status"),
                                                ""));
                            }
                        }
                    }

                    if (source != null)
                        tableHeader.setValueEnum(source);


                    if (initialValue != null)
                        tableHeader.setInitialValue(initialValue);


                    String name = field.getName();
                    tableHeader.setKey(name);
                    tableHeader.setDataIndex(name);

                    String headerTooltip = dirtField.tooltip();
                    tableHeader.setTooltip(headerTooltip);


                    // dirt search -------------------------------------------
                    DirtSearch[] dirtSearches = dirtField.dirtSearch();
                    if (dirtSearches.length != 0) {
                        DirtSearch dirtSearch = dirtSearches[0];
                        DirtSearchType dirtSearchType = new DirtSearchType(tableHeader);
                        tableHeader.setSearchType(dirtSearchType);

                        dirtSearchType.setValueType(dirtSearch.valueType().toString());
                        dirtSearchType.setOperator(dirtSearch.operator().toString());
                    }

                    //  dirt submit--------------------------------------------
                    DirtSubmit[] submitables = dirtField.dirtSubmit();
                    if (submitables.length != 0) {
                        DirtSubmit submitable = submitables[0];
                        DirtSubmitType dirtSubmitType = new DirtSubmitType(tableHeader);
                        tableHeader.setSubmitType(dirtSubmitType);
                        dirtSubmitType.setSubmitable(true);
                        try {
                            ColProps colProps = submitable.colProps().newInstance();
                            dirtSubmitType.setColProps(colProps);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        dirtSubmitType.setPlaceholder(submitable.placeholder());
                        dirtSubmitType.setWidth(submitable.width().getValue());
                        dirtSubmitType.setIndex(submitable.index());
                        dirtSubmitType.setValueType(uiTypeStr);
                        HashMap formItemProps = new HashMap();

                        // 兼容 JSR
                        ArrayList<Map> rules = DirtRuleAnnotationConvertor.parseRules(field);
                        if (rules != null && rules.size() > 0) {
                            formItemProps.put("rules", rules);
                            dirtSubmitType.setFormItemProps(formItemProps);
                        }

                        // 很重要，不然ProForm 提交时拿不到值
                        assert name != null && name.length() > 0;
                        dirtSubmitType.setKey(name);


                        dirtSubmitType.setTooltip(headerTooltip);

                        // WARNING. using dirtField title for submit label
                        String submitlable = dirtField.title();
                        if (submitlable.length() == 0) {
                            submitlable = name;
                        }
                        dirtSubmitType.setTitle(submitlable);

                        if (source != null)
                            dirtSubmitType.setValueEnum(source);
                        if (initialValue != null)
                            dirtSubmitType.setInitialValue(initialValue);


                        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
                        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
                        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                        ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                        DirtEntityType dirtEntity = null;
                        if (oneToMany != null || manyToMany != null) {
                            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                            if (actualTypeArguments.length == 1) {
                                Type actualTypeArgument = actualTypeArguments[0];
                                Class aClass = null;
                                try {
                                    aClass = Class.forName(actualTypeArgument.getTypeName());
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                                DirtContext context = applicationContext.getBean(DirtContext.class);
                                dirtEntity = context.getDirtEntity(aClass.getName());

                            }
                        } else if (manyToOne != null || oneToOne != null) {

                            Class<?> ret = fieldRetType;
                            DirtContext context = applicationContext.getBean(DirtContext.class);
                            dirtEntity = context.getDirtEntity(ret.getName());

                        }
                        if (dirtEntity != null) {
                            //List<DirtFieldType> heads = dirtEntity.getHeads();
                            //ArrayList<Object> objects = new ArrayList<>();

                            //HashMap<Object, Object> columns = new HashMap<>();
                            //columns.put("uiType", "group");
                            //columns.put("columns", heads.stream()
                            //        .filter(dirtFieldType -> dirtFieldType.getSubmitType() != null)
                            //        .map(DirtFieldType::getSubmitType).collect(Collectors.toList()));
                            //objects.add(columns);

                            //dirtSubmitType.setColumns(objects);

                        }
                        if (oneToMany != null)
                            tableHeader.setRelation(eDirtEntityRelation.OneToMany);
                        else if (oneToOne != null)
                            tableHeader.setRelation(eDirtEntityRelation.OneToOne);
                        else if (manyToOne != null)
                            tableHeader.setRelation(eDirtEntityRelation.ManyToOne);
                        else if (manyToMany != null)
                            tableHeader.setRelation(eDirtEntityRelation.ManyToMany);

                    }
                    return tableHeader;
                })
                .collect(Collectors.toList());

        if (this.actionMap.size() > 0) {
            ArrayList<String> names = new ArrayList<>(this.actionMap.keySet());
            DirtFieldType action = new DirtFieldType();
            action.setKey("option");
            action.setValueType("option");
            action.setFixed("right");

            //  每个中文字符给个10px，差不多了
            int counts = 0;
            for (String name : names) {
                counts += name.length();
            }
            action.setWidth(counts * 10 + names.size() * 5 + "px");
            action.setIndex(9999);

            action.setTitle("操作");
            action.setActions(this.actionMap);


            this.heads.add(action);
        }
        //  排序 header
        this.heads.sort(Comparator.comparingInt(DirtFieldType::getIndex));

    }

    private void initActionMap() {
        for (Method declaredMethod : this.entityClass.getDeclaredMethods()) {
            DirtAction actionAnnotation = declaredMethod.getAnnotation(DirtAction.class);
            if (actionAnnotation != null) {
                // actionAnnotation 的参数只应该包含 primitive 类型
                DirtActionType dirtActionType = new DirtActionType();
                dirtActionType.setMethod(declaredMethod);
                dirtActionType.setText(actionAnnotation.text());
                dirtActionType.setDesc(actionAnnotation.desc());
                dirtActionType.setKey(actionAnnotation.key());

                Parameter[] parameters = declaredMethod.getParameters();
                if (parameters.length > 0) {
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        List<DirtFieldType> dirtFieldTypes = this.fromParameter(parameter);
                        dirtActionType.getArgColumnsMap().put(parameter.getName(), dirtFieldTypes);
                    }


                }
                this.actionMap.put(actionAnnotation.key(), dirtActionType);
            }
        }
    }

    private List<DirtFieldType> fromParameter(Parameter parameter) {
        // 这里有顺序问题，
        // DirtContext 收集 DirtEntity 过程中，会调用 this.dirtContet.getDirtEntity，很可能拿不到还拿到的 DirtEntity.
        return this.dirtContext.getDirtEntity(parameter.getType().getName()).getHeads();
    }

    public void initDirtFieldMap() {
        for (Field declaredField : this.entityClass.getDeclaredFields()) {
            DirtField dirtField = declaredField.getAnnotation(DirtField.class);
            if (dirtField != null) {
                this.dirtFieldMap.put(declaredField.getName(), dirtField);
            }
        }
    }

    public DirtActionType getAction(String name) {
        DirtActionType dirtActionType = this.actionMap.get(name);
        if (dirtActionType == null) throw new RuntimeException("DirtActionType" + name + "不存在");
        return dirtActionType;
    }

    public DirtField getDirtField(String filedName) {
        return this.dirtFieldMap.get(filedName);
    }

    private void initIdOfEntityMap() {
        this.dirtFieldMap.forEach((s, dirtField) -> {
            Class<? extends DirtBaseIdEntity>[] classes = dirtField.idOfEntity();
            if (classes.length > 0) {
                Class<? extends DirtBaseIdEntity> aClass = classes[0];
                this.idOfEntityMap.put(s, aClass);
            }
        });
    }

}
