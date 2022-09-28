package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveAttr extends DirtBaseIdEntity {

    @DirtField(title = "属性值",  dirtSubmit = @DirtSubmit)
    @NotNull
    Long val;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @DirtField(title = "属性类型",idOfEntity = ReserveAttrType.class)
    @JoinColumn(name = "reserveAttrTypeId")
    ReserveAttrType reserveAttrType;

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}




}
