package com.zk.dirt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.MetaTableProvider;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DirtEntity("元数据")
@DynamicUpdate
@DynamicInsert
@Table(name = "metatype")
@SQLDelete(sql = "UPDATE metatype SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = MetaType.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class MetaType extends DirtBaseIdEntity {

    @DirtField(
            uiType = eUIType.select, enumProvider = MetaTableProvider.class)
    String tableName;
    @DirtField
    String columnName;
    @DirtField
    String title;
    @DirtField
    Boolean search;

    @DirtField
    Boolean enable;
    @DirtField
    // 在查询表单中不展示此项
    Boolean hideInSearch;
    @DirtField
    // 在 Table 中不展示此列
    Boolean hideInTable;
    @DirtField
    // 在 Form 中不展示此列
    Boolean  hideInForm;

    @DirtField
    Boolean nullable;
    @DirtAction(text = "详情")
    public void detail() {
    }

    @DirtAction(text = "删除")
    public void delete() {
    }

    @DirtAction(text = "编辑")
    public void edit() {
    }


}
