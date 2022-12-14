package com.zk.dirt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.MetaTableProvider;
import com.zk.dirt.TableColumnsProvider;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtDataSource;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.defaults.BaseDefaultValue;
import com.zk.dirt.intef.iEnumText;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
public class MetaType extends DirtBaseIdEntity {


    @DirtField(title = "实体全名",tooltip = "影响的实体名",
            uiType = eUIType.selectLiveInput, fixed = DirtField.eFixedType.LEFT,
            datasource = @DirtDataSource(value = MetaTableProvider.class)
    )
    String tableName;

    @DirtField(
            title = "列名",
            uiType = eUIType.selectLiveInput,
            tooltip = "影响的列名",
            fixed = DirtField.eFixedType.LEFT,
            datasource = @DirtDataSource(dependsColumn = "tableName", value = TableColumnsProvider.class)
    )
    String columnName;


    @DirtField(title = "新列名", tooltip = "仅改变显示，不影响内部逻辑")
    String title;

    @DirtField(title = "是否可搜索",tooltip = "是否在搜索表单里显示")
    @Column(nullable = false,columnDefinition="bit default false COMMENT '是否可搜索, false: 不可 true: 可'")
    Boolean search;

    @DirtField(title = "是否启用",tooltip = "是否启用, 默认启用",defaultValue = BaseDefaultValue.TRUE.class)
    @Column(nullable = false,columnDefinition="bit default false COMMENT '是否启用, false: 不可 true: 可'")
    Boolean enable ;


    public enum eConstrain implements iEnumText {
        NONE("不约束"),
        EMAIL("邮箱格式"),
        NOTNULLOREMPTY("非null 且不为空串")
        ;

        String text;

        eConstrain(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }

    @DirtField(title = "约束条件")
    eConstrain constrainType;

    //@DirtField(title = "是否必须存在",tooltip = "是否不可为 null, 默认可为 null, 在已运行的系统里切换此属性要非常慎重！")
    //@Column(nullable = false,columnDefinition="bit  COMMENT '是否必须存在, false: 不可 true: 可'")
    //Boolean mandate;

    //@DirtField(title = "分组名", uiType =  eUIType.selectInputMultipal, value = MetaGroupProvider.class )
    //String groupName;
    //
    //@DirtField(title = "分组排序")
    //Integer groupIndex;


    @DirtAction(text = "详情")
    public void detail() {
    }

    @DirtAction(text = "删除")
    public void delete() {
    }

    @DirtAction(text = "编辑")
    public void edit() {
    }


    @PostRemove
    @PostPersist
    @PostUpdate
    public void post(){
        //DirtContext dirtContext = SpringUtil.getApplicationContext().getBean(DirtContext.class);
        //dirtContext.removeOptionFunctionKey(tableName,columnName);
    }

    @PrePersist
    @PreUpdate
    public void pre() {

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
