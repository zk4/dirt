package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class ManyToManySide1 extends DirtBaseIdEntity {

    @DirtField
    @NotEmpty
    @Size(min = 2, max = 30)
    String name1;


    //@DirtField(
    //        title = "side",
    //        uiType = eValueType.formList,
    //        dirtSubmit = @DirtSubmit
    //)
    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //List<ManyToManySide2> sides;

    // TODO:
    //  1. idOfEntity 可省略，可从 Field 的 type 推导
    @DirtField(idOfEntity = ManyToManySide2.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
     List<ManyToManySide2> sides2;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}

}
