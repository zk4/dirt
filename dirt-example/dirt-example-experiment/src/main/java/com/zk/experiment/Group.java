package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("会员组")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_group")
@SQLDelete(sql = "UPDATE mms_group SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Group.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Group extends DirtBaseIdEntity {



    @DirtField(title = "会员集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_group_member_rel",
            joinColumns={@JoinColumn(name="groupId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;

    @DirtField(title = "群主")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    Member owner;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}



}
