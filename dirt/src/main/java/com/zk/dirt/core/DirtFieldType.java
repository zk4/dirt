package com.zk.dirt.core;

import com.zk.dirt.experiment.ColProps;
import lombok.Data;

import java.util.Map;

// https://procomponents.ant.design/components/table#columns-%E5%88%97%E5%AE%9A%E4%B9%89
// https://procomponents.ant.design/components/table?current=1&pageSize=5#valuetype-%E5%80%BC%E7%B1%BB%E5%9E%8B
// https://ant.design/components/table-cn/#API
@Data
public class DirtFieldType {

    // 与 antd 中基本相同，但是支持通过传入一个方法
    String title;

    Integer index;

    // 会在 title 之后展示一个 icon，hover 之后提示一些信息
    String tooltip;

    // 是否自动缩略
    Boolean ellipsis;

    // 是否支持复制
    Boolean copyable;

    // 值的枚举，会自动转化把值当成 key 来取出要显示的内容
    // https://procomponents.ant.design/components/schema/#valueenum
    Map valueEnum;

    // 值的类型,会生成不同的渲染器，
    // 见 eUIType
    String valueType;

    // 查询表单中的权重，权重大排序靠前
    Integer order;

    // 查询表单的 props，会透传给表单项,如果渲染出来是 Input,就支持 input 的所有 props，
    // 同理如果是 select，也支持 select 的所有 props。也支持方法传入
    Map fieldProps;

    // 传递给 Form.Item 的配置,可以配置 rules，但是默认的查询表单 rules 是不生效的。需要配置 ignoreRules
    Map formItemProps;

    ColProps colProps = new ColProps();

    // 配置列的搜索相关，false 为隐藏
    Boolean search;

    String width="md";

    // 在编辑表格中是否可编辑的，函数的参数和 table 的 render 一样
    Boolean editable;

    // 一个表单项占用的格子数量, 占比= colSize*span，colSize 默认为 1 ，span 为 8，span是form={{span:8}} 全局设置的
    Integer colSize;

    // 在查询表单中不展示此项
    Boolean hideInSearch;

    // 在 Table 中不展示此列
    Boolean hideInTable;

    // 在 Form 中不展示此列
    Boolean  hideInForm;

    // 在 Descriptions 中不展示此列
    Boolean hideInDescriptions;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成
    Boolean filters;

    // 筛选表单，为 true 时使用 ProTable 自带的，为 false 时关闭本地筛选
    // https://procomponents.ant.design/components/table?current=1&pageSize=5&title=a&state=open 不知道是个啥，文档得看一下
    Boolean onFilter;

    // 从服务器请求枚举
    // Object request;
    Boolean sorter;
    // 查询表单项初始值
    Object initialValue;

    // 列设置中disabled的状态
    Boolean disable;

    // 列数据在数据项中对应的路径，支持通过数组查询嵌套路径
    // https://ant.design/components/table-cn/#API
    String dataIndex;

    // React 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性
    String key;

    // （IE 下无效）列是否固定，可选 true (等效于 left) left right
    // https://ant.design/components/table-cn/#API
    String fixed;

    // 在构造提交表单时的属性
    DirtSubmitType submitType;

    // 在构造搜索表单时的属性
    DirtSearchType searchType;

    Map actions;

    String idOfEntity;

    // JPA 映射关系
    eDirtEntityRelation relation= eDirtEntityRelation.None;

    // subtree Name
    String subTreeName;


    // 在有 relation 时，onetomany 与 manytomany 按 column 排序就很尴尬。先禁了，有场景再说
    public Boolean getSearch() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany)
            return false;
        return search;
    }

    public Boolean getFilters() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany)
            return false;
        return filters;
    }

    public Boolean getOnFilter() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany)
            return false;

        return onFilter;
    }

    public Boolean getSorter() {
        if(relation == eDirtEntityRelation.OneToMany || relation == eDirtEntityRelation.ManyToMany)
            return false;

        return sorter;
    }
}
