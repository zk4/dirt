package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eUIType;
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
@DirtEntity
@DynamicUpdate

@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Card extends DirtBaseIdEntity {


    @DirtField(title = "卡名")
    String name;

    @DirtField(title = "卡类型",
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select d.entries from DictionaryIndex as d where d.name='卡类型\'"),
            dirtSubmit = @DirtSubmit
    )

    String  cardType;

    @DirtField
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="card_benifit_rel",
            joinColumns={@JoinColumn(name="cardId")},
            inverseJoinColumns={@JoinColumn(name="benifitId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Benifit> benifits;

    //@Data
    //@AllArgsConstructor
    //static class Id  {
    //    Long id;
    //}
    //@DirtField(idOfEntity = Benifit.class,dirtSubmit = {})
    //@ManyToMany
    //@Transient
    //Set<Id>  benifitIds;
    //

    @DirtField
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="member_card_rel",
            joinColumns={@JoinColumn(name="cardId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    // json 仅序列化为 id，避免循环
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;
    //
    //@DirtField(dirtSubmit = {})
    //@Transient
    //Set<Id>  memberds;
    //
    //@PostLoad
    //public void  postLoad(){
    //    this.benifitIds= benifits.stream().map(b ->new Id(b.getId())).collect(Collectors.toSet());
    //    this.memberds= members.stream().map(m ->new Id(m.getId())).collect(Collectors.toSet());
    //}

    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}



}
