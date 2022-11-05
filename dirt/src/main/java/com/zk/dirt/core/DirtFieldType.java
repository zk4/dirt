package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.experiment.ColProps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

// https://procomponents.ant.design/components/table#columns-%E5%88%97%E5%AE%9A%E4%B9%89
// https://procomponents.ant.design/components/table?current=1&pageSize=5#valuetype-%E5%80%BC%E7%B1%BB%E5%9E%8B
// https://ant.design/components/table-cn/#API
@Data
@ApiModel(value = "schema")
public class DirtFieldType {
    @JsonIgnore
    MetaType metaType;
    @ApiModelProperty(value = "标题")
    String title;

    @ApiModelProperty(value = "排序")
    Integer index;

    @ApiModelProperty(value = "tooltip, 通常需要在 title 之后展示一个 icon，hover 之后提示一些信息")
    String tooltip;

    @ApiModelProperty(value = "是否自动缩略")
    Boolean ellipsis;

    @ApiModelProperty(value = " 是否支持复制")
    Boolean copyable;

    // 值的枚举，会自动转化把值当成 key 来取出要显示的内容
    @ApiModelProperty(value = "渲染组件，静态值的枚举 https://procomponents.ant.design/components/schema/#valueenum")
    Map valueEnum;

    @ApiModelProperty(value = "前端据此选择不同的组件进行渲染")
    String valueType;

    @ApiModelProperty(value = "查询表单中的权重，权重大排序靠前")
    Integer order;

    @ApiModelProperty(value = "查询表单的 props，会透传给表单项,如果渲染出来是 Input,就支持 input 的所有 props，同理如果是 select，也支持 select 的所有 props。也支持方法传入")
    Map fieldProps;

    @ApiModelProperty(value = " 传递给 Form.Item 的配置,可以配置 rules，但是默认的查询表单 rules 是不生效的。需要配置 ignoreRules")
    Map formItemProps;

    @ApiModelProperty(value = "所占栅格大小")
    ColProps colProps = new ColProps();

    @ApiModelProperty(value = "配置列的搜索相关，false 为隐藏")
    Boolean search;

    @ApiModelProperty(value = "表头所占比例")
    String width="md";

    @ApiModelProperty(value = "在编辑表格中是否可编辑的，函数的参数和 table 的 render 一样")
    Boolean editable;

    @ApiModelProperty(value = "一个表单项占用的格子数量, 占比= colSize*span，colSize 默认为 1 ，span 为 8，span是form={{span:8}} 全局设置的")
    Integer colSize;

    @ApiModelProperty(value = "在查询表单中是否展示此项")
    Boolean hideInSearch;

    @ApiModelProperty(value = "在 Table 中是否展示此列")
    Boolean hideInTable;

    @ApiModelProperty(value = "在 Form 中不展示此列")
    Boolean  hideInForm;

    @ApiModelProperty(value = "在 Descriptions 中不展示此列")
    Boolean hideInDescriptions;

    @ApiModelProperty(value = "表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成")
    Boolean filters;


    // https://procomponents.ant.design/components/table?current=1&pageSize=5&title=a&state=open 不知道是个啥，文档得看一下
    @ApiModelProperty(value = "筛选表单，为 true 时使用 ProTable 自带的，为 false 时关闭本地筛选")
    Boolean onFilter;

    @ApiModelProperty(value = "排序")
    Boolean sorter;

    @ApiModelProperty(value = "查询表单项初始值")
    Object initialValue;

    @ApiModelProperty(value = "列设置中disabled的状态")
    Boolean disable;

    @ApiModelProperty(value = "列数据在数据项中对应的路径，支持通过数组查询嵌套路径 https://ant.design/components/table-cn/#API")
    String dataIndex;

    @ApiModelProperty(value = "React 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性")
    String key;

    @ApiModelProperty(value = "IE 下无效）列是否固定，可选 true (等效于 left) left right. https://ant.design/components/table-cn/#API")
    String fixed;

    @ApiModelProperty(value = "依赖列，当列改变时，应该重新获取 feild 的 schema 数据")
    String[] dependColumns;

    @ApiModelProperty(value = "在构造提交表单时的属性")
    DirtSubmitType submitType;

    @ApiModelProperty(value = "在构造搜索表单时的属性")
    DirtSearchType searchType;

    @ApiModelProperty(value = "动作")
    Map actions;

    @ApiModelProperty(value = "id 所对应的实体名，在 relation 有值时才有意义")
    String idOfEntity;

    @ApiModelProperty(value = "ER 映射关系")
    eDirtEntityRelation relation= eDirtEntityRelation.None;


    @ApiModelProperty(value = "孩子节点名")
    String subTreeName;


    //  构造时，传入 column 元数据，可动态更改当前 schema 状态
    public DirtFieldType(MetaType metaType) {
        this.metaType = metaType;
    }



    public Boolean getSearch() {
        // 在有 relation 时，onetomany 与 manytomany 按 column 排序就很尴尬。先禁了，有场景再说
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany) {
            return false;
        }
        // 优先元数据
        if (metaType != null) {
            return metaType.getSearch();
        }
        return search;
    }

    public Boolean getFilters() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany) return false;
        return filters;
    }

    public Boolean getOnFilter() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany) return false;
        return onFilter;
    }

    public Boolean getSorter() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany) return false;
        return sorter;
    }



    public String getTitle() {
        if (metaType != null) return metaType.getTitle();
        return title;
    }

    //public Boolean getHideInSearch() {
    //    if (metaType != null) return metaType.getHideInSearch();
    //    return hideInSearch;
    //}
    //
    //public Boolean getHideInTable() {
    //    if (metaType != null) return metaType.getHideInTable();
    //    return hideInTable;
    //}
    //
    //public Boolean getHideInForm() {
    //    if (metaType != null) return metaType.getHideInForm();
    //    return hideInForm;
    //}
}

