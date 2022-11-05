package com.zk.dirt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.MetaGroupProvider;
import com.zk.dirt.MetaTableProvider;
import com.zk.dirt.TableColumnsProvider;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtDepends;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
//@JsonIdentityInfo(scope = MetaType.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class MetaType extends DirtBaseIdEntity {


    @DirtField(title = "实体全名", uiType = eUIType.select, dataSource = MetaTableProvider.class)
    String tableName;

    @DirtField(
            title = "column 名",
            uiType = eUIType.selectSearhInput,
            depends = @DirtDepends(onColumn = "tableName", dependsProvider = TableColumnsProvider.class)
    )
    String columnName;

    @DirtField(title = "column 重命名", tooltip = "仅改变显示，不影响内部逻辑")
    String title;

    @DirtField(title = "是否可搜索")
    Boolean search;

    @DirtField(title = "是否启用")
    Boolean enable;

    @DirtField(title = "是否必须存在")
    Boolean mandate;

    @DirtField(title = "分组名", uiType =  eUIType.selectInputMultipal, dataSource = MetaGroupProvider.class )
    String groupName;

    @DirtField(title = "分组排序")
    Integer groupIndex;

    @DirtAction(text = "详情")
    public void detail() {
    }

    @DirtAction(text = "删除")
    public void delete() {
    }

    @DirtAction(text = "编辑")
    public void edit() {
    }


    @PrePersist
    @PreUpdate
    public void prePersist() {
        // FIXME:  以下代码有环调用，再说吧。
        //DirtContext dirtContext = SpringUtil.getApplicationContext().getBean(DirtContext.class);
        //DirtEntityType dirtEntity = dirtContext.getDirtEntity(this.tableName);
        //DirtField dirtField = dirtEntity.getDirtField(this.columnName);
        //if(dirtField==null){
        //    throw new RuntimeException("无此字段");
        //}
        //if (!dirtField.metable())
        //    throw new RuntimeException("未在 DirtField 注解里开启 metable 属性。如果以前可以，可以由于版本更新去除了" +
        //            this.tableName + "." + this.columnName+
        //            "的 metable 属性，请联系开发人员");

    }

}
