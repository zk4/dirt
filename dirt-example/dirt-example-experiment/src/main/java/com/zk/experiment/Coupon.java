package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@DirtEntity("优惠券")
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class Coupon extends DirtBaseIdEntity {


    @DirtField(title = "券名称")
    @NotEmpty
    @Size(min = 1, max = 30)
    String name;

    @DirtField(title = "券描述",uiType = eUIType.textarea)
    @Size(min = 1, max = 3000)
    String remark;

    @DirtField(title = "券类型",
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select d.entries from DictionaryIndex as d where d.name='券类型\'"),
            dirtSubmit = @DirtSubmit
    )
    String  couponType;


    @DirtField(title = "生效时间",

            uiType = eUIType.dateTime,
            dirtSearch = @DirtSearch(
                    title = "生效时间",
                    valueType = eUIType.dateTimeRange
            )
    )
    protected LocalDateTime startTime;


    @DirtField(title = "失效时间",

            uiType = eUIType.dateTime,
            dirtSearch = @DirtSearch(
                    title = "失效时间",
                    valueType = eUIType.dateTimeRange
            )
    )
    protected LocalDateTime endTime;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}


}
