package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.experiment.ColProps;
import com.zk.dirt.rule.DirtRules;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// https://procomponents.ant.design/components/table#columns-%E5%88%97%E5%AE%9A%E4%B9%89
// https://procomponents.ant.design/components/table?current=1&pageSize=5#valuetype-%E5%80%BC%E7%B1%BB%E5%9E%8B
// https://ant.design/components/table-cn/#API
@Getter
@Setter
public class DirtSubmitType {
    @JsonIgnore
    DirtFieldType fieldType;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    String width;
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    Integer index;
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    String key;
    @ApiModelProperty(value = "占位符")
    String placeholder;
    @ApiModelProperty(value = "是否可提交")
    Boolean submitable = false;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    String valueType;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    String title;
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    String tooltip;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    Map valueEnum;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    Map formItemProps;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    ColProps colProps = new ColProps();

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    Object initialValue;

    @ApiModelProperty(value = "是否必填")
    Boolean required;

    // OneToMany 的定义
    @ApiModelProperty(value = "嵌套定义")
    Object columns;

    @ApiModelProperty(value = "子节点名称")
    String subTreeName;

    public DirtSubmitType(DirtFieldType fieldType, DirtSubmit submitable, ArrayList<Map> rules) {
        this.fieldType = fieldType;
        //this.metaType = metaType;
        this.setSubmitable(true);
        try {
            ColProps colProps = submitable.colProps().newInstance();
            this.setColProps(colProps);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.setPlaceholder(submitable.placeholder());
        this.setWidth(submitable.width().getValue());
        this.setIndex(submitable.index());
        this.setValueType(submitable.valueType().toString());
        formItemProps = new HashMap();
        formItemProps.put("rules", new ArrayList<>());
        // 兼容部分 JSR 303
        if (rules != null && rules.size() > 0) {
            formItemProps.put("rules", rules);
            //this.setFormItemProps(formItemProps);
        }

        // 很重要，不然 ProForm 提交时拿不到值
        String name = fieldType.dataIndex;
        assert name != null && name.length() > 0;
        this.setKey(name);

        if (initialValue != null)
            this.setInitialValue(initialValue);
    }







    public String getTitle() {
        if (fieldType != null && fieldType.getMetaType() != null && fieldType.getMetaType().getTitle()!=null && fieldType.getMetaType().getTitle().length()!=0) return fieldType.getMetaType().getTitle();
        if (fieldType != null) return fieldType.getTitle();
        return title;
    }

    public Map getFormItemProps() {
        if (fieldType != null && fieldType.getMetaType() != null) {
            MetaType metaType = fieldType.getMetaType();
            if (metaType != null) {
                MetaType.eConstrain constrainType = metaType.getConstrainType();
                if (constrainType == MetaType.eConstrain.NOTNULLOREMPTY) {
                    Map notEmpty = DirtRules.createNotEmpty(getKey() + "不可为空");
                    ArrayList<Map> objects = new ArrayList<>();
                    objects.add(notEmpty);
                    formItemProps.put("rules", objects);
                }
                if (constrainType == MetaType.eConstrain.EMAIL) {
                    Map email = DirtRules.createEmail(getKey(), "必须为邮箱格式");
                    ArrayList<Map> objects = new ArrayList<>();
                    objects.add(email);
                    formItemProps.put("rules", objects);
                }
            }
        }
        return formItemProps;
    }

    public String getIdOfEntity() {
        return this.fieldType.getIdOfEntity();
    }

    public eDirtEntityRelation getRelation() {
        return this.fieldType.getRelation();
    }

    public String getTooltip() {
        if (this.tooltip == null && this.fieldType != null) {
            return this.fieldType.tooltip;
        }
        return tooltip;
    }

    public Map getValueEnum() {
        if (this.valueEnum == null && this.fieldType != null) {
            return this.fieldType.valueEnum;
        }
        return valueEnum;
    }

    public String getValueType() {
        if (this.valueType == null && this.fieldType != null) {
            return this.fieldType.valueType;
        }
        return valueType;
    }

    public String getSubTreeName() {
        if (this.subTreeName == null && this.fieldType != null) {
            return this.fieldType.subTreeName;
        }
        return subTreeName;
    }

    public String getDependColumn() {
        if (this.fieldType != null) {
            return this.fieldType.dependColumn;
        }
        return null;
    }

    public String getKey() {
        if(fieldType!=null){
            return fieldType.getPrefix() + key;
        }
        return key;
    }
}
