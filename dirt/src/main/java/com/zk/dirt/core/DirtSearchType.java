package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

// UI 搜索组件信息
@Data
public class DirtSearchType {

    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    private String title;
    @JsonIgnore
    private DirtFieldType fieldType;
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    private String valueType;    //ProFieldValueType	数据的渲渲染方式，我们自带了一部分，你也可以自定义 uiType
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    private Map valueEnum;//	(Entity)=> ValueEnum | ValueEnum	支持 object 和 Map，Map 是支持其他基础类型作为 key
    @ApiModelProperty(value = "见 dirtfieldType 定义，但具有高优先级")
    private Map formItemProps;//	(form,config)=>formItemProps | formItemProps	传递给 Form.Item 的配置

    @ApiModelProperty(value = "搜索操作符，当前未实现")
    private String operator;
    public DirtSearchType(DirtFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getTitle() {
        if (StringUtils.isEmpty(this.title)   && this.fieldType != null) {
            return this.fieldType.title;
        }
        return title;
    }

    public String getValueType() {
        if (this.valueType == null && this.fieldType != null) {
            return this.fieldType.valueType;
        }
        return valueType;
    }

    public Map getValueEnum() {
        if (this.valueEnum == null && this.fieldType != null) {
            return this.fieldType.valueEnum;
        }
        return valueEnum;
    }

    public Map getFormItemProps() {
        if (this.formItemProps == null && this.fieldType != null) {
            return this.fieldType.formItemProps;
        }
        return formItemProps;
    }
}
