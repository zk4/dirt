package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
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
@Table(name = "mall_attr_group")
@SQLDelete(sql = "UPDATE mall_attr_group SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = AttrGroup.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class AttrGroup extends DirtBaseIdEntity {

    /**
     * 组名
     */
    @DirtField(title = "组名")
    private String name;
    /**
     * 排序
     */
    @DirtField(title = "排序")
    private Integer sort;
    /**
     * 描述
     */
    @DirtField(title = "描述")
    private String descript;
    /**
     * 组图标
     */
    @DirtField(title = "组图标")
    private String icon;

    @DirtField(title = "所属分类")
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;



}
