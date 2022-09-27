package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("优惠券")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_coupon")
@SQLDelete(sql = "UPDATE mms_coupon SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Coupon.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Coupon extends DirtBaseIdEntity {


    @DirtField(title = "券名称")
    @NotEmpty
    @Size(min = 1, max = 30)
    String name;

    @DirtField(title = "券描述",uiType = eUIType.textarea)
    @Size(min = 1, max = 3000)
    String remark;


    @DirtField(title = "拥有者")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_coupon_rel",
            joinColumns={@JoinColumn(name="couponId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;


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


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
