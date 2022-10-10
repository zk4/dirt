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
import org.hibernate.annotations.*;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter

@DirtEntity("会员标签")
@javax.persistence.Entity(name = "t_tag")
@DynamicUpdate
@DynamicInsert
@Table(appliesTo = "t_tag",comment = "会员标签")
@SQLDelete(sql = "UPDATE t_tag SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Tag.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Tag extends DirtBaseIdEntity {


    @DirtField(title = "标称名称" )
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;



    @DirtField(title = "会员集合")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name="mms_member_tag_rel",
            joinColumns={@JoinColumn(name="tagId")},
            inverseJoinColumns={@JoinColumn(name="memberId")})
    @JsonIdentityReference(alwaysAsId = true)
    Set<Member> members;

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
