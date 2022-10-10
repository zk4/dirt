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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity("权益")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_benefit")
@SQLDelete(sql = "UPDATE mms_benefit SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
//@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Benefit extends DirtBaseIdEntity {


    @DirtField(title = "权益名称")
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @DirtField(title = "卡包",dirtSubmit = {})
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_card_benefit_rel",
            joinColumns={@JoinColumn(name="benefitId")},
            inverseJoinColumns={@JoinColumn(name="cardId")})
    Set<Card> cards;

    @DirtField(title = "权益集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_benefit_rel",
            joinColumns={@JoinColumn(name="benefitId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;


    @DirtField(title = "次数限制")
    Long maxCounts;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}



}
