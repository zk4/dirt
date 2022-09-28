package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.experiment.ColProps;
import lombok.Data;

import java.util.Map;

// https://procomponents.ant.design/components/table#columns-%E5%88%97%E5%AE%9A%E4%B9%89
// https://procomponents.ant.design/components/table?current=1&pageSize=5#valuetype-%E5%80%BC%E7%B1%BB%E5%9E%8B
// https://ant.design/components/table-cn/#API
@Data
public class DirtSubmitType {
    @JsonIgnore
    DirtFieldType fieldType;

    public DirtSubmitType(DirtFieldType fieldType) {
        this.fieldType = fieldType;
    }

    String width;
    Integer index;
    String key;
    String placeholder;
    Boolean submitable=false;
    // String key;	//React.key	确定这个列的唯一值,一般用于 dataIndex 重复的情况
    // dataIndex;	//React.key | React.key[]	与实体映射的 key，数组会被转化 [a,b] => Entity.a.b
    String valueType;	//ProFieldValueType	数据的渲渲染方式，我们自带了一部分，你也可以自定义 uiType
    String title;//	ReactNode |(props,type,dom)=> ReactNode	标题的内容，在 form 中是 label
    String tooltip;//	string	会在 title 旁边展示一个 icon，鼠标浮动之后展示
    Map valueEnum;//	(Entity)=> ValueEnum | ValueEnum	支持 object 和 Map，Map 是支持其他基础类型作为 key
    //Map fieldProps;//	(form,config)=>fieldProps| fieldProps	传给渲染的组件的 props，自定义的时候也会传递
    Map formItemProps;//	(form,config)=>formItemProps | formItemProps	传递给 Form.Item 的配置
    //Map proFieldProps;//	proFieldProps	设置到 ProField 上面的 props，内部属性
    //Boolean hideInDescriptions;//	boolean	在 descriptions 中隐藏
    //Boolean hideInForm;//	boolean	在 Form 中隐藏
    //Boolean hideInTable;//	boolean	在 Table 中隐藏
    //Boolean hideInSearch;//	boolean	在 Table 的查询表格中隐藏
    //columns;//	ProFormColumnsType[] | (values) => ProFormColumnsType[]	嵌套子项，uiType 为 dependency 时，请使用(values) => ProFormColumnsType[]其他情况使用 ProFormColumnsType[]
    ColProps colProps = new ColProps();//	ColProps	在开启 grid 模式时传递给 Col
    //Map rowProps;//	RowProps	开启栅格化模式时传递给 Row

    Object initialValue;

    Boolean required;

    // OneToMany 的定义
    Object columns;


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

    public String getValueType() {
        if (this.valueType == null && this.fieldType != null) {
            return this.fieldType.valueType;
        }
        return valueType;
    }
}
