package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.config.entity.valid.AddGroup;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@DirtEntity(value = "品牌")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_brand")
@SQLDelete(sql = "UPDATE mall_brand SET deleted = true WHERE id=? and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Brand.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Brand extends DirtBaseIdEntity {

    /**
     * 品牌名
     */
    @DirtField(title = "品牌名")
    @NotBlank(message = "品牌名必须提交")
    private String name;
    /**
     * 品牌logo地址
     */
    @DirtField(title = "品牌logo地址",uiType = eUIType.imageUploader)
    @URL(message = "logo必须是一个合法的url地址")
    private String logo;
    /**
     * 介绍
     */
    @DirtField(title = "介绍")
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */

    @DirtField(title = "显示状态")
    private Boolean showStatus;
    /**
     * 检索首字母
     */
    @DirtField(title = "检索首字母")
    @NotEmpty(groups={AddGroup.class})
    @Pattern(regexp="^[a-zA-Z]$",message = "检索首字母必须是一个字母")
    private String firstLetter;
    /**
     * 排序
     */
    @DirtField(title = "排序")
    @NotNull
    @Min(value = 0,message = "排序必须大于等于0")
    private Integer sort;

    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
