package com.zk.experiment.entity;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iEnumText;
import com.zk.dirt.util.SpringUtil;
import com.zk.mall.entity.Menu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("会员")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_member")
@SQLDelete(sql = "UPDATE mms_member SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
// @SQLDelete(sql="UPDATE mms_member SET deleted=true WHERE id=? and version=? ")
// @Where(clause="date_deletion IS NULL ")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Member.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Member extends DirtBaseIdEntity {


    @DirtField(title = "真实姓名",metable = true)
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @DirtField(title =  "目录级",subTreeName = "subMenus", uiType = eUIType.text,dirtSearch = @DirtSearch(uiType = eUIType.cascader))
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Menu menu;

    @DirtField(title = "会员昵称" )
    @NotEmpty
    @Size(min = 2, max = 30)
    String nickname;

    @DirtField(title = "手机号")
    @NotEmpty
    @Size(max = 16)
    String phoneNumber;

    enum  eGender implements  iEnumText{
        FEMALE("女性"),
        MALE("男性");

        String text;

        eGender(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }
    @DirtField(title = "性别")
    eGender gender;

    @DirtField(title = "会员生日")
    LocalDate birthday;

    @DirtField(title = "会员住址")
    String address;


    @DirtField(title = "卡包")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_card_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="cardId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Card> cards;

    @DirtField(title = "券包")
    @ManyToMany(cascade = CascadeType.ALL)
    // 允许双向更新
    @JoinTable(name="mms_member_coupon_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="couponId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Coupon> coupons;

    @DirtField(title = "分组")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_group_member_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="groupId")})
    @JsonIdentityReference(alwaysAsId = true)

    Set<Group> groups;

    @DirtField(title = "用户 id")
    @OneToOne
    @JoinColumn(name="UserId")
    User user;

    @ManyToOne
    @JoinColumn(name="enterpriseParentId")
    Member enterpriseParent;

    @DirtField(title = "企业会员组")
    @OneToMany
    // 允许双向更新
    @JoinColumn(name="enterpriseParentId")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> enterpriseMembers;

    enum MemberType implements iEnumText {
        ENTERPISE("企业会员"),
        PERSONAL("个人会员");

        String text;

        MemberType(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }

    @DirtField(title = "会员类型")
    @Enumerated(value = EnumType.STRING)
    MemberType  memberType;


    @DirtField(title = "标签集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_tag_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="tagId")})
    @JsonIdentityReference(alwaysAsId = true)

    Set<Tag> tags;


    @DirtField(title = "权益集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_benefit_rel",
            joinColumns={@JoinColumn(name="memberId")},
            inverseJoinColumns={@JoinColumn(name="benefitId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Benefit> benefits;


    @DirtField(title = "卡数",dirtSubmit = {},search = false,sorter = false)
    @Transient
    Integer cardCounts;



    public Integer getCardCounts(){
        return  this.cards !=null ?this.cards.size():0;
    }

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

    @DirtAction(text = "随机名称",   confirm = true)
    public void reduce() {
        this.nickname = RandomStringUtils.randomAlphabetic(13);
    }

    @DirtAction(text = "haha",   confirm = true)
    public void haha() {
       this.phoneNumber ="xxxxx";
    }


    @DirtField(title = "联合字段（名字-昵称)",dirtSubmit = {})
    @Transient
    String fullName;

    public String getFullName(){
        return name +"-" +nickname;
    }

    @PreUpdate
    @PrePersist
    public void prePersisit(){
        if(this.memberType == MemberType.PERSONAL){
            if(this.enterpriseMembers!=null &&  this.enterpriseMembers.size()!=0){
                throw new RuntimeException("如果是个人会员，不能有企业子会员");
            }
            if(this.enterpriseParent!=null) {
                throw new RuntimeException("如果是个人会员，不能有企业父会员");
            }
        }
    }

    @Data
    @DirtEntity(visiable = false)
    static class WithArgsData {
        @DirtField(title = "会员姓名")
        @NotEmpty
        @Size(min = 2, max = 30)
        String name;

        @DirtField(title = "会员小名")
        @NotEmpty
        @Size(min = 2, max = 30)
        String littlename;

        @DirtField(title = "性别2", uiType = eUIType.select)
        @Enumerated
        eGender gender2;

        @DirtField(title = "会员 id",
                idOfEntity = Member.class,
                relation = eDirtEntityRelation.OneToOne,
                dirtSubmit = @DirtSubmit
        )
        @Column(name = "member")
        Long memberId;


    }
    // dynamic action metaData
    // treat method as field, getter function returning boolean decide if action name show
    @JsonProperty
    public Boolean getWithArgs() {
        return this.name.length()>2;
    }

    @DirtAction(text = "短名")
    public void withArgs() {
        this.name="无2";
        SpringUtil.getApplicationContext().publishEvent("hello");

    }

    // dynamic action metaData
    // treat method as field, getter function returning boolean decide if action name show
    @JsonProperty
    public Boolean getWithArgs2() {
        return this.name.length()<=2;
    }

    @DirtAction(text = "长名")
    public void withArgs2() {
        this.name="三个字2222";
        SpringUtil.getApplicationContext().publishEvent("hello");

    }
    @DirtAction(text = "带参")
    public void withArgs3(@DirtArg("args") WithArgsData args) {
        this.name=args.name;
        SpringUtil.getApplicationContext().publishEvent("hello");

    }


    @Data
    @DirtEntity(visiable = false)
    static public class VerificationData {
        @DirtField(title = "权益模板 id")
        @NotNull
        Long benefitId;
        @JsonIgnore
        Member member;
    }

    @Transient
    @JsonIgnore
    boolean isVerification;

    public Boolean getVerification() {
        return this.benefits.size()>0;
    }

    @DirtAction(text = "核销")
    public void verification(@DirtArg("args") VerificationData args) {


        args.setMember(this);
        Boolean  found = false;
        for (Benefit benefit : this.benefits) {
            if(benefit.getId() == args.benefitId)
                found = true;
        }
        if(!found){
            throw new  RuntimeException("该会员无此权益");
        }
        SpringUtil.getApplicationContext().publishEvent(args);
    }
}
