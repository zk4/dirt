package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
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

    @DirtField
    @ManyToMany
    @JsonIdentityReference(alwaysAsId = true)
    Set<Benifit> benifits;

    @Data
    @AllArgsConstructor
    static class Id  {
        Long id;
    }
    //@DirtField(idOfEntity = Benifit.class,dirtSubmit = {})
    //@ManyToMany
    //@Transient
    //Set<Id>  benifitIds;
    //

    @DirtField
    @ManyToMany(mappedBy = "cards")
    @JsonIgnore
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
