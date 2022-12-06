package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.member.entity.types.eReseveProductType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveFinance extends DirtBaseIdEntity {

    @DirtField(title = "预约名称", index = -99, dirtSubmit = @DirtSubmit(index = -99))
    String name;



    @DirtField(
            title = "概要描述",
            uiType = eUIType.text,
            dirtSubmit =
            @DirtSubmit(
                    index = 3,
                    placeholder = "概要描述",
                    width = eSubmitWidth.LG
                    //rules = @DirtFomItemRule()
            )
    )
    String description;



    // https://kostenko.org/blog/2020/10/jpa-manytoone-get-id-one-query.html
    //---------------------------many to one 演示  ------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @DirtField(title = "预留时间")
    ReserveTimeTemplate reserveTimeTemplate;


    @DirtField(
            title = "剧场类型",
            uiType = eUIType.select,
            dirtSubmit = @DirtSubmit())
    @Enumerated
    eReseveProductType etype;


    @DirtField(
            title = "属性值例表",
            width = eSubmitWidth.LG,
            uiType = eUIType.formList,
            dirtSubmit = @DirtSubmit(width = eSubmitWidth.LG)
    )
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ReserveAttr> attrs;

    //
    //@DirtField(
    //        title = "销售属性值",
    //        width = eSubmitWidth.LG,
    //        uiType = eDirtFormUIType.formList,
    //        dirtSubmit = @DirtSubmit(width = eSubmitWidth.LG)
    //)
    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //List<ReserveAttr> salesAttrs;
    //


    @DirtField(
            title = "时间限制",
            width = eSubmitWidth.LG,
            uiType = eUIType.formList,
            dirtSubmit = @DirtSubmit(width = eSubmitWidth.LG)
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ReserveTimeTemplate> reserveTimeTemplates;

    @DirtField(
            title = "状态",

            uiType = eUIType.select,
            dirtSubmit = @DirtSubmit())
    String status;

}
