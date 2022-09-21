package com.zk.member.entity;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member_group_rel", uniqueConstraints = {@UniqueConstraint(columnNames = {"m_id", "g_id"})})
@Getter
@Setter
@DirtEntity
public class DoMemberGroupRelation extends DirtBaseIdEntity {

    @DirtField
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "m_id")
    private DoMember member;

    @DirtField
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "g_id")
    private DoGroup anGroup;

    @DirtField
    @JoinColumn()
    @Column(name="other_value")
    private int otherValue;
}
