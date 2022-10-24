package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iEnumText;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DirtEntity("属性分组")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_attr")
@SQLDelete(sql = "UPDATE mall_attr SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = Attr.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Attr extends DirtBaseIdEntity {

    /**
     * 组名
     */
    @DirtField(title = "组名")
    private String name;

    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    @DirtField(title = "是否需要检索")
    private Boolean searchType;
    /**
     * 属性图标
     */
    @DirtField(title = "属性图标",uiType = eUIType.imageUploader)
    private String icon;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */
    public  enum eType implements iEnumText {
        SALE("销售属性"),
        BASIC("基本属性"),
        ALL("都有");

        private  String text;

        eType(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }}
    private eType attrType;

    @DirtField(title = "所属分类")
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    @DirtField(title = "是否展示在介绍上")
    private Boolean showDesc;


}
