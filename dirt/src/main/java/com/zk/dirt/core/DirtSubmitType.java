package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.experiment.ColProps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

// https://procomponents.ant.design/components/table#columns-%E5%88%97%E5%AE%9A%E4%B9%89
// https://procomponents.ant.design/components/table?current=1&pageSize=5#valuetype-%E5%80%BC%E7%B1%BB%E5%9E%8B
// https://ant.design/components/table-cn/#API
@Data
public class DirtSubmitType {
    @JsonIgnore
    MetaType metaType;
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
    Boolean submitable=false;

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    eUIType valueType;

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

    public DirtSubmitType(DirtFieldType fieldType, MetaType metaType) {
        this.fieldType = fieldType;
        this.metaType = metaType;
    }

    public String getTitle() {
        if (metaType != null) return metaType.getTitle();
         return title;
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

    public eUIType getValueType() {
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
}
