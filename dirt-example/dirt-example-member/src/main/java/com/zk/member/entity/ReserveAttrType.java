package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.member.entity.types.eUnit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveAttrType extends DirtBaseIdEntity {

    @DirtField(title = "属性名",  dirtSubmit = @DirtSubmit)
    @NotNull
    String name;




    @DirtField(title = "单位", uiType = eUIType.select,enumListableType = eUnit.class)
    @Enumerated(EnumType.STRING)
    @NotNull
    eUnit unit;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}



}
