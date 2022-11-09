package com.zk.dirt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtDepends;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@DirtEntity("元数据")
@DynamicUpdate
@DynamicInsert
@Table(name = "metatype", uniqueConstraints = {@UniqueConstraint(columnNames = {"tableName", "columnName","deleted"})})
//@SQLDelete(sql = "UPDATE metatype SET deleted = true WHERE id=?  and version=? ")
//@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class MetaType2 extends DirtBaseIdEntity {


    @DirtField(title = "实体全名",tooltip = "影响的实体名",
            el = "#{@myService.getByID('hello')}",
            uiType = eUIType.selectLiveInput, fixed = DirtField.eFixedType.LEFT,
            depends = @DirtDepends(dataSource = MetaTableProvider.class)
    )
    String tableName;
    //
    //@DirtField(
    //        title = "列名",
    //        uiType = eUIType.selectLiveInput,
    //        tooltip = "影响的列名",
    //        fixed = DirtField.eFixedType.LEFT,
    //        depends = @DirtDepends(onColumn = "tableName", dataSource = TableColumnsProvider.class)
    //)
    //String columnName;
    //
    //
    //@DirtField(title = "新列名", tooltip = "仅改变显示，不影响内部逻辑")
    //String title;
    //
    //@DirtField(title = "是否可搜索",tooltip = "是否在搜索表单里显示")
    //@Column(nullable = false,columnDefinition="bit default false COMMENT '是否可搜索, false: 不可 true: 可'")
    //Boolean search;
    //
    //@DirtField(title = "是否启用",initialValue = 1,tooltip = "是否启用, 默认启用")
    //@Column(nullable = false,columnDefinition="bit default false COMMENT '是否启用, false: 不可 true: 可'")
    //Boolean enable;
    //
    //
    //public enum eConstrain implements iEnumText {
    //    NONE("不约束"),
    //    EMAIL("邮箱格式"),
    //    NOTNULLOREMPTY("非null 且不为空串")
    //    ;
    //
    //    String text;
    //
    //    eConstrain(String text) {
    //        this.text = text;
    //    }
    //
    //    @Override
    //    public Object getText() {
    //        return text;
    //    }
    //}
    //
    //@DirtField(title = "约束条件")
    //eConstrain constrainType;
    //
    //
    //@DirtAction(text = "详情")
    //public void detail() {
    //}
    //
    //@DirtAction(text = "删除")
    //public void delete() {
    //}
    //
    //@DirtAction(text = "编辑")
    //public void edit() {
    //}
    //
    //
    //@PostRemove
    //@PostPersist
    //@PostUpdate
    //public void post(){
    //
    //}
    //
    //@PrePersist
    //@PreUpdate
    //public void pre() {
    //
    //
    //
    //}

}
