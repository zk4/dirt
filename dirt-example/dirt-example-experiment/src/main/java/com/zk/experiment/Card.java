package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @ManyToMany(mappedBy = "cards")
    @JsonIgnore
    Set<Member> members;


    @DirtField
    @Transient
    Set<Long>  memberidsds;

    @PostLoad
    public void  afterMemberIds(){
        Stream<Long> longStream = members.stream().map(member -> member.getId());
        this.memberidsds= longStream.collect(Collectors.toSet());
    }

    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}



}
