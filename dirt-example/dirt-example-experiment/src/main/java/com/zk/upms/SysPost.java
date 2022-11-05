package com.zk.upms;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
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
@DirtEntity("岗位")
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_dept")
@SQLDelete(sql = "UPDATE sys_dept SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SysPost extends MyBaseIdNameEntity<SysPost> {



    /** 岗位排序 */
    @DirtField(title = "岗位排序")
    private Integer postSort;

    @PreUpdate
    @PrePersist
    public void preUpdateAndPersist(){
        if(this.children!=null
                && this.children.size()>0)
            isLeaf = false;
        else
            isLeaf = true;
    }
    @ManyToOne
    @JsonIgnore
    SysPost parent;

    @DirtField(title = "孩子节点")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysPost> children;


    @Override
    public String genCode() {
        return "Post_"+getSnowId();
    }


}
