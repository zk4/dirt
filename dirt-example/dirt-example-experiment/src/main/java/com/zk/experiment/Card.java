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
@DirtEntity("卡")
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

    @DirtField(title = "权益集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="card_benifit_rel",
            joinColumns={@JoinColumn(name="cardId")},
            inverseJoinColumns={@JoinColumn(name="benifitId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Benifit> benifits;



    @DirtField(title = "会员集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="member_card_rel",
            joinColumns={@JoinColumn(name="cardId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    // json 仅序列化为 id，避免循环
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;


    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}

    @DirtField(title = "状态")
    Boolean status ;

    @DirtField(title = "实名")
    Boolean binding= true;

    @DirtAction(text = "状态改变", key = "changeStatus")
    public void changeStatus() {

        if (this.status==null)
            this.status = false;
        this.status = !this.status;
    }


    @DirtAction(text = "切换绑定", key = "bind")
    public void bind() {
        if (this.binding==null)
            this.binding = false;
        this.binding = !this.binding;
    }

}
