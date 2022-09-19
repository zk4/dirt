package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.member.entity.types.eFormField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class FormField extends BaseIdEntity2 {

    @DirtField
    String name;


    @DirtField(title = "控件类型", uiType = eUIType.treeSelect)
    eFormField formFieldType;

    // 与 antd 中基本相同，但是支持通过传入一个方法
    @DirtField
    String title;


    // 会在 title 之后展示一个 icon，hover 之后提示一些信息
    @DirtField(title = "提示")
    String tooltip;

    // 是否自动缩略
    @DirtField(title = "自动缩略")
    Boolean ellipsis;

    // 是否支持复制
    @DirtField(title = "是否可复制")
    Boolean copyable;

    // 配置列的搜索相关，false 为隐藏
    @DirtField(title = "是否可搜索")
    Boolean search;


    @DirtField(title = "查询表单显示")
    Boolean hideInSearch;

    // 在 Table 中不展示此列
    @DirtField(title = "Table 显示")
    Boolean hideInTable;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成
    @DirtField(title = "表头筛选")
    Boolean filters;
}
