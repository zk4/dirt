package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.experiment.eSubmitWidth;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SmartForm extends BaseIdEntity2 {

    @DirtField(title = "name",  dirtSubmit = @DirtSubmit)
    @NotNull
    @Size(min = 5,max = 20)
    String name;


    @DirtField(
            title = "注释",

            uiType = eUIType.textarea,
            dirtSubmit =
            @DirtSubmit(
                    index = 1000,
                    placeholder = "写注释",
                    width = eSubmitWidth.LG
                    //rules = @DirtFomItemRule()
            )
    )
    String comments;
    @DirtField(
            title = "字段例表",
            width = eSubmitWidth.LG,
            uiType = eUIType.formList,
            dirtSubmit = @DirtSubmit(width = eSubmitWidth.LG)
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<FormField> fields;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}


}
