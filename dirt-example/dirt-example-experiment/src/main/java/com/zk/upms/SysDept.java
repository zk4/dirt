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
@DirtEntity("部门")
@DynamicUpdate
@DynamicInsert
@Table(name = "upms_sys_Dept")
@SQLDelete(sql = "UPDATE upms_sys_Dept SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysDept.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SysDept extends MyBaseIdEntity {
    @Override
    public String genCode() {
        return "Dept_"+getSnowId();
    }

    @Data
    @AllArgsConstructor
    public static class IdNameObj {
        Long id;
        String name;
        Boolean isLeaf;
    }

    @Transient
    SysMenu.IdNameObj idNameObj;

    public SysMenu.IdNameObj getIdNameObj() {
        return new SysMenu.IdNameObj(this.id,this.name,this.isLeaf);
    }

    Boolean isLeaf;

    @PreUpdate
    @PrePersist
    public void preUpdateAndPersist(){
        if(subDepts!=null
                && this.subDepts.size()>0)
            isLeaf = false;
        else
            isLeaf = true;
    }


    @DirtField(title = "目录名" )
    @NotEmpty
    @Size(max = 30)
    String name;

    @ManyToOne
    @JsonIgnore
    SysDept parent;

    @DirtField(title = "子部分")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysDept> subDepts;

    /** 祖级列表 */

    private String ancestors;

    /** 部门名称 */
    private String deptName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父部门名称 */
    private String parentName;


}
