package com.zk.upms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.intef.iEnumText;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Entity
@DirtEntity("用户")
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_user")
@SQLDelete(sql = "UPDATE sys_user SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysUser.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SysUser extends MyBaseIdEntity {

    /**
     * 部门ID
     */
    @DirtField(title = "部门")
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private SysDept dept;

    /**
     * 用户账号
     */
    @DirtField(title = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @DirtField(title = "用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @DirtField(title = "用户邮箱")
    @Email
    private String email;

    /**
     * 手机号码
     */
    @DirtField(title = "手机号码")
    private String phonenumber;


    enum eGender implements  iEnumText{
        MALE("男"),
        FEMAIL("女");
        String text;

        eGender(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }

    /**
     * 用户性别
     */
    @DirtField(title = "用户性别")
    private eGender sex;

    /**
     * 用户头像
     */
    @DirtField(title = "用户头像",uiType = eUIType.avatar)
    private String avatar;

    /**
     * 密码
     */
    @DirtField(title = "密码",uiType = eUIType.password)
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */

    private String status;



    /**
     * 最后登录IP
     */

    private String loginIp;

    /**
     * 最后登录时间
     */
    @DirtField
    private LocalDate loginDate;


    /**
     * 岗位组
     */

    @DirtField(title = "岗位组")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="upms_user_post_rel",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="postId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysPost> posts;


    @DirtField(title = "权限")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="upms_user_role_rel",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="roleId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysRole> roles;

    @Override
    public String genCode() {
        return "User_"+getSnowId();
    }

}
