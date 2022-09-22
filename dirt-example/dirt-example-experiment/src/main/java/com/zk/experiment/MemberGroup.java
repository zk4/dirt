package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("会员组")
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MemberGroup extends DirtBaseIdEntity {



    @DirtField(title = "会员集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="membergroup_member_rel",
            joinColumns={@JoinColumn(name="memberGroupId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    Set<Member> members;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}



}
