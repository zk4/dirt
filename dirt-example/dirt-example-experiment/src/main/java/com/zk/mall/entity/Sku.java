package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DirtEntity("Sku")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_sku")
@SQLDelete(sql = "UPDATE mall_sku SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = Sku.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Sku extends DirtBaseIdEntity {


    @DirtField
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    Sku sku;

    /**
     * sku名称
     */
    @DirtField(title = "sku名称")
    private String name;
    /**
     * sku介绍描述
     */
    @DirtField(title = "sku介绍描述")
    private String skuDesc;

    /**
     * 默认图片
     */
    @DirtField(title = "默认图片",uiType = eUIType.imageUploader)
    private String skuDefaultImg;
    /**
     * 标题
     */
    @DirtField(title = "标题")
    private String skuTitle;
    /**
     * 副标题
     */
    @DirtField(title = "副标题")

    private String skuSubtitle;
    /**
     * 价格
     */
    @DirtField(title = "价格")

    private BigDecimal price;
    /**
     * 销量
     */
    @DirtField(title = "销量")

    private Long saleCount;

}
