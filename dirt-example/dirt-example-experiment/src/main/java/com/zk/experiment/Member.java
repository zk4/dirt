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
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("会员")
@DynamicUpdate
@DynamicInsert
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Member.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member extends DirtBaseIdEntity {


    @DirtField(title = "会员姓名"    )
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @DirtField(title = "会员昵称"    )
    @NotEmpty
    @Size(min = 2, max = 30)
    String nickname;

    @DirtField(title = "卡包")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="member_card_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="cardId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Card> cards;

    @DirtField(title = "券包")
    @ManyToMany(cascade = CascadeType.MERGE)
    // 允许双向更新
    @JoinTable(name="member_coupon_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="couponId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Coupon> coupons;

    @DirtField(title = "会员组")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="my_group_member_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="myGroupId")})
    @JsonIdentityReference(alwaysAsId = true)

    Set<MyGroup> myGroups;

    @DirtField(title = "会员类型",
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select d.entries from DictionaryIndex as d where d.name='会员类型'"),
            dirtSubmit = @DirtSubmit
    )

    String  memberType;


    @DirtField(title = "标签集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="member_tag_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="tagId")})
    @JsonIdentityReference(alwaysAsId = true)

    Set<Tag> tags;



    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

    @DirtAction(text = "随机名称", key = "reduce", confirm = true)
    public void reduce() {
        this.nickname = RandomStringUtils.randomAlphabetic(13);
    }


    @DirtField(title = "联合字段（名字-昵称)",dirtSubmit = {})
    @Transient
    String fullName;

    public String getFullName(){
        return name +"-" +nickname;
    }

}
