package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@Entity
@DirtEntity("目录")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_menu")
@SQLDelete(sql = "UPDATE t_menu SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Menu.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public class Menu extends DirtBaseIdEntity {
    @Data
    @AllArgsConstructor
    public static class IdNameObj {
        Long id;
        String name;
    }

    @Transient
    IdNameObj idNameObj;

    public IdNameObj getIdNameObj() {
        return new  IdNameObj(this.id,this.name);
    }

    @DirtField(title = "目录名" )
    @NotEmpty
    @Size(max = 30)
    String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="parentId")
    Menu parent;

    @DirtField(title = "子目录")
    @OneToMany
    @JoinColumn(name = "parentId")
    //@JsonIdentityReference(alwaysAsId = true)
    Set<Menu> children;

    public Set<Menu> getChildren() {
        return children;
    }

    @Transient
    Long key;
    static AtomicLong integer=new AtomicLong(1);
    public Long getKey() {
        return integer.incrementAndGet();
    }

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
