package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eUIType;
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
@DirtEntity("卡")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_card")
@SQLDelete(sql = "UPDATE mall_card SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")

@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = Card.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Card extends DirtBaseIdEntity {


    @DirtField(title = "卡名")
    String name;

    @DirtField(title = "卡类型",
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select d.entries from DictionaryIndex as d where d.name='卡类型\'"),
            dirtSubmit = @DirtSubmit
    )

    String  cardType;

    @DirtField(title = "会员集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="member_card_rel",
            joinColumns={@JoinColumn(name="cardId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    // json 仅序列化为 id，避免循环
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

    @DirtField(title = "状态")
    Boolean status ;

    @DirtField(title = "实名")
    Boolean binding= true;

    @DirtAction(text = "状态改变")
    public void changeStatus() {

        if (this.status==null)
            this.status = false;
        this.status = !this.status;
    }


    @DirtAction(text = "切换绑定")
    public void bind() {
        if (this.binding==null)
            this.binding = false;
        this.binding = !this.binding;
    }

}
