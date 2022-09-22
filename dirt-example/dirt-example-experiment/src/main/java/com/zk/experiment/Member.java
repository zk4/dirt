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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class Member extends DirtBaseIdEntity {


    @DirtField(title = "会员姓名"    )
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @DirtField
    @ManyToMany
    Set<Card> cards;

    @DirtField
    @ManyToMany
    Set<Coupon> coupons;

    @DirtField(title = "会员类型",
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select d.entries from DictionaryIndex as d where d.name='会员类型'"),
            dirtSubmit = @DirtSubmit
    )

    String  memberType;
    //



    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}


}
