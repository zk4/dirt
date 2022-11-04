package com.zk.upms;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
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

@Getter
@Setter
@Entity
@DirtEntity("岗位")
@DynamicUpdate
@DynamicInsert
@Table(name = "upms_sys_dept")
@SQLDelete(sql = "UPDATE upms_sys_dept SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysPost.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public class SysPost extends MyBaseIdEntity {


    @Data
    @AllArgsConstructor
    public static class IdNameObj {
        Long id;
        String name;
        Boolean isLeaf;
    }

    @Transient
    IdNameObj idNameObj;

    public IdNameObj getIdNameObj() {
        return new IdNameObj(this.id,this.name,this.isLeaf);
    }

    Boolean isLeaf;

    @PreUpdate
    @PrePersist
    public void preUpdateAndPersist(){
        if(this.subPosts!=null
                && this.subPosts.size()>0)
            isLeaf = false;
        else
            isLeaf = true;
    }


    @DirtField(title = "岗位名" )
    @NotEmpty
    @Size(max = 30)
    String name;

    @ManyToOne
    @JsonIgnore
    SysPost parent;

    @DirtField(title = "子岗")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysPost> subPosts;

    /** 岗位编码 */
    @DirtField(title = "岗位编码")
    private String postCode;

    /** 岗位排序 */
    @DirtField(title = "岗位排序")
    private Integer postSort;

    @Override
    public String genCode() {
        return "Post_"+getSnowId();
    }
}
