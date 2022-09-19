package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.member.entity.types.eReseveProductType;
import com.zk.member.provider.StatusProvider;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveProduct extends BaseIdEntity2 {

    @DirtField(title = "场馆活动名称", tooltip = "C端展示的名称，可能是演出的名称，剧场的名称，剧目的名称", index = -99, dirtSubmit = @DirtSubmit(index = -99))
    String name;


    @DirtField(title = "场馆编号", tooltip = "场馆唯一标识")
    String code;

    @DirtField(
            title = "概要描述",
            tooltip = "C端展示的名称",
            uiType = eUIType.text,
            dirtSubmit = @DirtSubmit(placeholder = "概要描述")
    )
    String description;

    @DirtField(
            title = "详细描述",
            tooltip = "详细描述",
            uiType = eUIType.text,
            dirtSubmit = @DirtSubmit(placeholder = "详细描述")
    )
    String detailDesc;


    @DirtField(title = "建议人数",  uiType = eUIType.digit)
    // TODO 实际约束根据库存来，也就是动态约束
    @Digits(integer = 99990, fraction = 0)
    Integer candidates;


    @DirtField(title = "场地位置", tooltip = "C端展示的地址")
    String locationDesc;

    @DirtField(title = "纬度")
    BigDecimal longtitude;

    @DirtField(title = "经度", uiType = eUIType.digit)
    BigDecimal latitude;


    @DirtField(title = "主图图片",uiType = eUIType.image  )
    String imgs;

    @DirtField(title = "剧场类型",uiType = eUIType.select)
    @Enumerated
    eReseveProductType etype;


    @DirtField(title = "属性值例表", idOfEntity = ReserveAttr.class)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<ReserveAttr> attrs;


    @DirtField(title = "属性manytomany", idOfEntity = ReserveAttr.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<ReserveAttr> attr2s;



    @DirtField(title = "时间限制",idOfEntity = ReserveTimeTemplate.class)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<ReserveTimeTemplate> reserveTimeTemplates;

    @DirtField(
            title = "状态",
            uiType = eUIType.select,
            enumProvider = {StatusProvider.class}
    )
    String status;

    @DirtAction(text = "详情", key = "detail")
    public void detail() {
    }

    @DirtAction(text = "删除", key = "delete")
    public void delete() {
    }

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {
    }

}
