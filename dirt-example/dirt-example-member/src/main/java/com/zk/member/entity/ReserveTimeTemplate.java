package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.member.entity.types.eWeekDay;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveTimeTemplate extends DirtBaseIdEntity {


    @DirtField(title = "开始时间",
            uiType = eUIType.time,
            dirtSubmit = @DirtSubmit

    )
    protected LocalTime startTime;

    @DirtField(title = "结束时间",
            uiType = eUIType.time,
            dirtSubmit = @DirtSubmit
    )
    protected LocalTime endTime;


    @DirtField(
            title = "可用星期",
            uiType = eUIType.checkbox,
            enumListableType= eWeekDay.class,
            dirtSubmit = @DirtSubmit())
    @ElementCollection
    List<eWeekDay>  days;



}
