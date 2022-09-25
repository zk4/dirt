package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eFilterOperator;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.member.entity.types.eGender;
import com.zk.member.entity.types.eIdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = {"idtype", "idnumber"})})
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class Member extends DirtBaseIdEntity {

    @DirtField
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Item> items;


    @DirtField(title = "会员昵称", dirtSearch = @DirtSearch(operator = eFilterOperator.LIKE))
    @NotEmpty
    @Size(min = 2, max = 30)

    String nickname;

    @DirtField(title = "会员姓名"    )
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @DirtField(title = "全名")
    @Transient
    String fullName;

    public String getFullName(){
        return name + nickname;
    }

    @DirtField(title = "证件类型", uiType = eUIType.select)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    eIdType idtype;

    @DirtField(title = "证件号码",  uiType = eUIType.text)
    @NotEmpty
    @Size(min = 2, max = 30)

    String idnumber;

    @DirtField(title = "性别",   uiType = eUIType.select)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    eGender gender;

    @DirtField(title = "性别2",   uiType = eUIType.select)
    @Enumerated
    eGender gender2;

    @DirtField(title = "会员手机号")
    @Size(max = 15, min = 11)
    @NotEmpty
    String phonenumber;


    @DirtField(title = "绑定")
    Boolean binding;





    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "githubRepo", insertable = false, updatable = false)
    //@JsonIgnore
    //GithubRepo githubRepo;

    //
    //@DirtField(title = "仓库名",
    //        idOfEntity = GithubRepo.class,
    //
    //        filters = true,
    //        onFilter = true,
    //        dirtSubmit = @DirtSubmit
    //)
    //@Column(name = "githubRepo")
    //Long githubRepoId;


    //@DirtField(title = "操作")
    //@DirtDefaultAction()
    //@Transient
    //@JsonIgnore
    //String action;


    // 如果是实体参与，一定是从服务器得到最新数据，而不是依赖前端传递。
    // 对于  row 的 action，默认当前 entity 要参与。
    // 什么时候显示 action 呢？
    // 参数： 不应该包含 entity 参数，这是成员函数，只应该操作自己的数据。
    @DirtAction(text = "切换绑定")
    public void bind() {
        if (this.binding==null)
            this.binding = false;
        this.binding = !this.binding;
    }

    @DirtAction(text = "随机昵称" , confirm = true)
    public void reduce() {
            this.nickname = RandomStringUtils.randomAlphabetic(13);
    }

    @Data
    @DirtEntity
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

    @DirtAction(text = "带参",   confirm = true)
    public void withArgs(WithArgsData args) {

        this.name = args.name;
        this.gender2 = args.gender2;

    }


    //@DirtAction(text = "下单", key = "makeOrder", confirm = true)
    //public void makeOrder(ReserveOrder args) {
    //    System.out.println(args);
    //}

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

}
