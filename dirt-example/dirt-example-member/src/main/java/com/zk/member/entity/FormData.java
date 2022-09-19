package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.dirt.core.eUIType;
import com.zk.member.entity.types.eGender;
import com.zk.member.provider.StatusProvider;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class FormData extends DirtBaseIdEntity {

    @DirtField(title = "name",  dirtSubmit = @DirtSubmit(

    ))
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
            title = "单标签",

            filters = true,
            onFilter = true,
            uiType = eUIType.treeSelect,
            enumProvider = {StatusProvider.class},
            dirtSubmit = @DirtSubmit())
    String singleStatus;

    @DirtField(
            title = "性别",

            filters = true,
            onFilter = true,
            uiType = eUIType.treeSelect,
            dirtSubmit = @DirtSubmit())
    @Enumerated
    eGender gender;
}
