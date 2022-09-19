package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class ManyToManySide2 extends BaseIdEntity2 {

    @DirtField
    @NotEmpty
    @Size(min = 2, max = 30)
    String name2;

    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}

}
