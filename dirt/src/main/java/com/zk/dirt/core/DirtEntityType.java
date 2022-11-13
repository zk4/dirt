package com.zk.dirt.core;

import com.zk.dirt.annotation.*;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.entity.iID;
import com.zk.dirt.intef.iDirtDictionaryEntryType;
import com.zk.dirt.intef.iEnumText;
import com.zk.dirt.rule.DirtRules;
import com.zk.dirt.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DirtEntityType {

    private Class<?> entityClass;

    private final Map<String, DirtField> dirtFieldMap = new HashMap<>();

    private final Map<String, DirtActionType> actionMap = new HashMap<>();

    private List<DirtFieldType> heads = new ArrayList<>();

    private final Map<String, Class> idOfEntityMap = new HashMap<>();

    private final Map<String, MetaType> metaCache = new HashMap<>();

    ApplicationContext applicationContext;

    DirtContext dirtContext;

    Boolean inited = false;

    public DirtEntityType(DirtContext context, ApplicationContext applicationContext, Class classAnnotationClass) {
        this.dirtContext = context;
        this.applicationContext = applicationContext;
        this.entityClass = classAnnotationClass;
        lazyInit();
    }

    public List<DirtFieldType> getHeads() {
        //lazyInit();
        return heads;
    }

    private void lazyInit() {
        // 可静态的应该尽量静态化
        if (!inited) {
            initDirtFieldMap();
            initIdOfEntityMap();
            initActionMap();
            initSchema();
            // TODO: 先不缓存，不然 metatype 不生效，回头再改成 lazy 形式
            //inited = true;
        }
    }


    private void initSchema() {

        List<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, entityClass);
        // TODO:
        // 统一 field， method 基于 DirtField 的构造
        // 实现类似 vue 里 computed 的效果
        List<Field> preFields = fields.stream()
                .filter(field -> field.getDeclaredAnnotation(DirtField.class) != null)

                // 不显示 metatype 禁用的数据
                .filter(field -> {
                    DirtField dirtField = field.getDeclaredAnnotation(DirtField.class);
                    MetaType metaType = getMetaType(field, dirtField);
                    // 如果 enable 为 false ，则不显示
                    if (metaType != null) {
                        return metaType.getEnable() != null ? metaType.getEnable() : true;
                    }
                    // 为 null，则放过，使用默认 column 信息
                    return true;
                }).collect(Collectors.toList());
        this.heads = preFields.stream()
                // 不处理 embedded ,后面再处理
                .filter(field -> field.getDeclaredAnnotation(Embedded.class) == null)
                .map(this::getFieldType)
                .collect(Collectors.toList());

        if (this.actionMap.size() > 0) {
            ArrayList<String> names = new ArrayList<>(this.actionMap.keySet());
            DirtFieldType action = new DirtFieldType(null);
            action.setKey("option");
            action.setValueType(eUIType.option.toString());
            action.setFixed("right");

            //  TODO： 应该由前端自己处理，不应该由服务端处理---------------------------start
            //  暂时每个中文字符给个9px，差不多了
            int counts = 0;
            for (String name : names) {
                counts += name.length();
            }
            action.setWidth(counts * 9 + names.size() * 2 + "px");
            //  TODO： 应该由前端自己处理，不应该由服务端处理---------------------------end

            action.setIndex(9999);
            action.setTitle("操作");
            action.setActions(this.actionMap);

            this.heads.add(action);
        }

        // -------------------------embedded 处理 开始----------------------------------start
        // 本质上就是将embedded 成员变量拍扁了，不做任何嵌套处理。各有各的好处吧。
        // 在名字上以 . 分隔
        // 如:
        // @Embedded
        // @DirtField
        // @AttributeOverrides({
        //         @AttributeOverride(name="longitude",column=@Column(name="destLongitude")),
        //         @AttributeOverride(name="latitude",column=@Column(name="destLatitude"))
        // })
        // Location destLocation;
        // 会输出  destLocation.longitude  与  destLocation.latitude 的  dirtfield

        // 处理 embedded
        List<Field> embeddedList = preFields.stream().filter(field -> field.getDeclaredAnnotation(Embedded.class) != null)
                .collect(Collectors.toList());
        for (Field field : embeddedList) {
            Type genericType = field.getGenericType();
            DirtEntityType dirtEntity = dirtContext.getDirtEntity(genericType.getTypeName());
            List<DirtFieldType> heads = dirtEntity.getHeads();
            for (DirtFieldType head : heads) {
                // 增加 prefix
                DirtField annotation = field.getAnnotation(DirtField.class);
                String title = annotation.title();
                String name = field.getName();
                if(title.length()>0){
                    name = title;
                }
                head.setPrefix(name +".");
            }
            this.heads.addAll(heads);
        }
        // -------------------------embedded 处理 结束----------------------------------end

        //  排序 header
        this.heads.sort(Comparator.comparingInt(DirtFieldType::getIndex));
    }

    public DirtFieldType getFieldType(String fieldName) {
        //TODO: duplicated code, optimize
        List<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, entityClass);
        for (Field field : fields) {
            if (field.getName().equals(fieldName))
                return getFieldType(field);
        }
        throw new RuntimeException("why here");
    }

    /**
     * @param field entity 的字段
     * @return
     */
    public DirtFieldType getFieldType(Field field) {
        DirtField dirtField = field.getDeclaredAnnotation(DirtField.class);

        MetaType metaType = getMetaType(field, dirtField);

        DirtFieldType tableHeader = new DirtFieldType(metaType);

        if (dirtField.title().length() == 0) {
            tableHeader.setTitle(field.getName());
        } else {
            tableHeader.setTitle(dirtField.title());
        }
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        Embedded embedded = field.getAnnotation(Embedded.class);

        tableHeader.setIndex(dirtField.index());
        tableHeader.setFixed(dirtField.fixed().getText());
        tableHeader.setEllipsis(dirtField.ellipsis());
        tableHeader.setCopyable(dirtField.copyable());
        tableHeader.setSearch(dirtField.search());
        tableHeader.setOnFilter(dirtField.onFilter());
        tableHeader.setFilters(dirtField.filters());
        tableHeader.setHideInTable(dirtField.hideInTable());
        tableHeader.setSorter(dirtField.sorter());


        // 设置　subTree　name，支持每个 field　都不一样的复杂模型
        String subTreeName = dirtField.subTreeName();
        if (subTreeName.length() > 0) {
            tableHeader.setSubTreeName(subTreeName);
        }

        DirtDepends[] depends = dirtField.depends();
        if (depends.length > 0) {
            DirtDepends depend = depends[0];
            String onColumn = depend.onColumn();
            tableHeader.setDependColumn(onColumn);
        }

        eUIType uiType = dirtField.uiType();
        Class<? extends iID>[] classes = dirtField.idOfEntity();
        Class<?> fieldRetType = field.getType();
        if (classes.length > 0) {
            Class<? extends iID> entityClass = classes[0];
            String simpleName = entityClass.getName();
            tableHeader.setIdOfEntity(simpleName);
        } else {
            //deduce Entity Type from Return Type if there is relations
            if (manyToOne != null || oneToOne != null) {
                tableHeader.setIdOfEntity(field.getType().getName());
            } else if (manyToMany != null || oneToMany != null) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1) {
                    Type actualTypeArgument = actualTypeArguments[0];
                    try {
                        Class aClass = Class.forName(actualTypeArgument.getTypeName());
                        tableHeader.setIdOfEntity(aClass.getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // set uiType  if value
        if (uiType == eUIType.auto) {
            // 根据返回值自动推导类型
            if (tableHeader.idOfEntity == null) {
                if (fieldRetType.isAssignableFrom(LocalDateTime.class)) uiType = eUIType.dateTime;
                else if (fieldRetType.isAssignableFrom(LocalDate.class)) uiType = eUIType.date;
                else if (fieldRetType.isAssignableFrom(Long.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(long.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(Integer.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(int.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(Float.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(float.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(Double.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(double.class)) uiType = eUIType.digit;
                else if (fieldRetType.isAssignableFrom(BigDecimal.class)) uiType = eUIType.money;
                else if (fieldRetType.isAssignableFrom(Boolean.class)) uiType = eUIType.switching;
                else if (fieldRetType.isAssignableFrom(boolean.class)) uiType = eUIType.switching;
            }
        }
        tableHeader.setValueType(uiType.toString());
        // 判断是否为枚举
        boolean enumConstant = fieldRetType.isEnum();
        if (enumConstant) {
            tableHeader.setValueType(eUIType.select.toString());
        }
        //-----------------------------------------
        // 设置 valueEnum, initialValue
        // 只关心 enum 类型
        Map source = new HashMap();
        Object initialValue = tableHeader.getInitialValue();

        // 枚举类型
        if (enumConstant) {
            boolean assignableFrom = iEnumText.class.isAssignableFrom(fieldRetType);
            // 实现了 iDirtListable 接口的枚举
            if (assignableFrom) {
                Class<? extends iEnumText> enumTextClass = fieldRetType.asSubclass(iEnumText.class);
                try {
                    iEnumText[] enumConstants = enumTextClass.getEnumConstants();
                    for (iEnumText value : enumConstants) {
                        source.put(value, new DirtEnumValue(value.getText(), value.toString(), ""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 纯枚举
            else {
                Field[] fields = fieldRetType.getFields();
                for (Field e : fields) {
                    String name = e.getName();
                    System.out.println(name);
                    source.put(name, new DirtEnumValue(name, name, ""));
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
                    if (option instanceof iDirtDictionaryEntryType) {
                        iDirtDictionaryEntryType option1 = (iDirtDictionaryEntryType) option;
                        source.put(option1.getDictKey(),
                                new DirtEnumValue(option1.getDictValue(),
                                        option1.getDictKey(),
                                        option1.getDictSort()
                                ));
                    } else {
                        Map option1 = (Map) option;
                        // TODO: 不可这样写，需要与实现无关
                        source.put(option1.get("dictKey"),
                                new DirtEnumValue(option1.get("dictValue"),
                                        option1.get("dictKey")
                                ));
                    }
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
            DirtSearchType dirtSearchType = new DirtSearchType(tableHeader, dirtSearch);
            tableHeader.setSearchType(dirtSearchType);
        }

        //  dirt submit--------------------------------------------
        DirtSubmit[] submitables = dirtField.dirtSubmit();
        if (submitables.length != 0) {
            DirtSubmit submitable = submitables[0];

            ArrayList<Map> rules = DirtRules.parseRules(field);
            DirtSubmitType dirtSubmitType = new DirtSubmitType(tableHeader, submitable, rules);
            tableHeader.setSubmitType(dirtSubmitType);


            if (oneToMany != null)
                tableHeader.setRelation(eDirtEntityRelation.OneToMany);
            else if (oneToOne != null)
                tableHeader.setRelation(eDirtEntityRelation.OneToOne);
            else if (manyToOne != null)
                tableHeader.setRelation(eDirtEntityRelation.ManyToOne);
            else if (manyToMany != null)
                tableHeader.setRelation(eDirtEntityRelation.ManyToMany);
            else if (embedded != null)
                tableHeader.setRelation(eDirtEntityRelation.Embedded);

        }
        return tableHeader;
    }

    private MetaType getMetaType(Field field, DirtField dirtField) {
        String name = field.getName();
        boolean b = metaCache.containsKey(name);
        MetaType metaType = null;
        if (b) {
            return metaCache.get(name);
        }
        if (dirtField.metable()) {
            EntityManager em = applicationContext.getBean(EntityManager.class);

            List<MetaType> resultList = em
                    .createQuery("SELECT m from MetaType as m where m.columnName = ?1", MetaType.class)
                    .setParameter(1, name)
                    .getResultList();
            if (resultList != null && resultList.size() != 0) {
                metaType = resultList.get(0);
            }

        }
        metaCache.put(name, metaType);
        return metaType;
    }

    public static Map<String, DirtActionType> getActionRecursively(Map<String, DirtActionType> out, Class<?> clazz, DirtContext dirtContext) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            DirtAction actionAnnotation = declaredMethod.getAnnotation(DirtAction.class);
            if (actionAnnotation != null) {
                // actionAnnotation 的参数只应该包含 primitive 类型
                DirtActionType dirtActionType = new DirtActionType();
                dirtActionType.setMethod(declaredMethod);
                dirtActionType.setText(actionAnnotation.text());
                dirtActionType.setDesc(actionAnnotation.desc());
                dirtActionType.setKey(declaredMethod.getName());

                Parameter[] parameters = declaredMethod.getParameters();

                ExceptionUtils.conditionThrow(parameters.length <= 1, "现在只支持一个参数，或没参数");

                if (parameters.length > 0) {
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        DirtEntityType dirtEntity = dirtContext.getDirtEntity(parameter.getType().getName());
                        List<DirtFieldType> dirtFieldTypes = dirtEntity.getHeads();
                        // FIXED: java 8 会保留 parameter 名字， java 11 会变成 arg0， arg 1
                        dirtActionType.getArgColumnsMap().put("args", dirtFieldTypes);
                    }
                }
                out.put(declaredMethod.getName(), dirtActionType);
            }
        }
        if (clazz.getSuperclass() != null)
            getActionRecursively(out, clazz.getSuperclass(), dirtContext);
        return out;
    }

    private void initActionMap() {
        this.actionMap.clear();
        getActionRecursively(this.actionMap, this.entityClass, dirtContext);
    }


    /**
     * 得到子类与所有父类的 field
     *
     * @param fields 返回值
     * @param type   目标 class
     * @return
     */
    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    // 递归父类拿 DirtField
    public static Map<String, DirtField> getFieldMapRecursively(Map<String, DirtField> out, Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            DirtField dirtField = declaredField.getAnnotation(DirtField.class);
            if (dirtField != null) {
                out.put(declaredField.getName(), dirtField);
            }
        }
        if (clazz.getSuperclass() != null)
            getFieldMapRecursively(out, clazz.getSuperclass());

        return out;

    }

    public void initDirtFieldMap() {
        this.dirtFieldMap.clear();
        getFieldMapRecursively(this.dirtFieldMap, this.entityClass);
    }

    public DirtActionType getAction(String name) {
        lazyInit();
        DirtActionType dirtActionType = this.actionMap.get(name);
        if (dirtActionType == null) throw new RuntimeException("DirtActionType" + name + "不存在");
        return dirtActionType;
    }

    public DirtField getDirtField(String filedName) {
        lazyInit();
        return this.dirtFieldMap.get(filedName);
    }

    public Map<String, Class> getIdOfEntityMap() {
        return idOfEntityMap;
    }

    private void initIdOfEntityMap() {
        this.dirtFieldMap.forEach((s, dirtField) -> {
            Class<? extends iID>[] classes = dirtField.idOfEntity();
            if (classes.length > 0) {
                Class<? extends iID> aClass = classes[0];
                this.idOfEntityMap.put(s, aClass);
            }
        });
    }

}
