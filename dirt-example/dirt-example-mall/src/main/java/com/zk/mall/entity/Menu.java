package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eDirtViewType;
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
@DirtEntity(value = "目录",viewType = eDirtViewType.Tree)
@DynamicUpdate
@DynamicInsert
@Table(name = "t_menu")
@SQLDelete(sql = "UPDATE t_menu SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Menu.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Menu extends DirtBaseIdEntity {

    @DirtField(title = "目录名" )
    @NotEmpty
    @Size(max = 30)
    String name;

    @ManyToOne
    @JsonIgnore
    Menu parent;

    @DirtField(title = "子目录")
    @OneToMany
    @JoinColumn(name = "parent")
    //@JsonIdentityReference(alwaysAsId = true)
    Set<Menu> subMenus;




    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
