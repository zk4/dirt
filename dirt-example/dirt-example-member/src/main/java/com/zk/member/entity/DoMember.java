package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@Table(name = "do_member")
@SQLDelete(sql = "UPDATE do_member SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class DoMember extends DirtBaseIdEntity {
    @DirtField(title = "会员名")
    @Size(max = 3)
    @NotEmpty
    String name;


    @DirtField(title = "是否实名")
    Boolean certificated;


    @OneToMany(mappedBy = "anGroup", fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JsonIgnore
    List<DoMemberGroupRelation> doMemberGroupRelations;



    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}


}
