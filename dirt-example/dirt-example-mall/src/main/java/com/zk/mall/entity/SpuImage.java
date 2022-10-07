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

@Getter
@Setter
@Entity
@DirtEntity("Spu图")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_spu_image")
@SQLDelete(sql = "UPDATE mall_spu_image SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = SpuImage.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SpuImage extends DirtBaseIdEntity {

    @DirtField(title = "spu")
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Spu spu;
    /**
     * 图片名
     */
    @DirtField(title = "图片名")
    private String imgName;
    /**
     * 图片地址
     */
    @DirtField(title = "图片地址",uiType = eUIType.imageUploader)
    private String imgUrl;
    /**
     * 顺序
     */
    @DirtField(title = "顺序")
    private Integer imgSort;
    /**
     * 是否默认图
     */
    @DirtField(title = "是否默认图")

    private Boolean defaultImg;


}
