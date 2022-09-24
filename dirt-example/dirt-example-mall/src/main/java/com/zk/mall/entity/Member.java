package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iListable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
@JsonIdentityInfo(scope = Member.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Member extends DirtBaseIdEntity {




    /**
     * 会员等级id
     */
    private Long levelId;

    @DirtField(title = "会员姓名")
    @NotEmpty
    @Size(max = 30)
    private String username;

    @DirtField(title = "密码")
    @NotEmpty
    @Size(max = 30)
    private String password;

    @DirtField(title = "昵称")
    @NotEmpty
    @Size(max = 30)
    private String nickname;


    @DirtField(title = "手机号码")
    @NotEmpty
    @Size(max = 30)
    private String mobile;

    @DirtField(title = "邮箱")
    @NotEmpty
    @Email
    private String email;

    @DirtField(title = "头像",uiType = eUIType.avatar)
    private String header;


    public enum  eGender implements iListable {
        MALE("男"),
        FEMALE("女");

        String msg;

        eGender(String msg) {
            this.msg = msg;
        }

        @Override
        public String getText() {
            return msg;
        }
    }

    @DirtField(title = "性别")
    private eGender gender;

    @DirtField(title = "生日",uiType = eUIType.date)
    private LocalDate birth;

    @DirtField(title = "所在城市")
    private String city;

    @DirtField(title = "职业")
    private String job;

    @DirtField(title = "个性签名")
    private String sign;

    @DirtField(title = "用户来源")
    private String sourceType;

    @DirtField(title = "积分",dirtSubmit = {})
    private Integer integration;

    @DirtField(title = "成长值",dirtSubmit = {})
    private Integer growth;



    @DirtField(title = "社交登录UID",dirtSubmit = {})
    private String socialUid;


    @DirtField(title = "社交登录TOKEN",dirtSubmit = {})
    private String accessToken;


    @DirtField(title = "社交登录过期时间",dirtSubmit = {})
    private long expiresIn;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}

    @PrePersist
    public void changeUuid(){
        System.out.println("");
    }
}
