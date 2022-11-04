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
@DirtEntity("菜单")
@DynamicUpdate
@DynamicInsert
@Table(name = "upms_sys_menu")
@SQLDelete(sql = "UPDATE upms_sys_menu SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysMenu.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public class SysMenu extends MyBaseIdEntity {

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
        return new  IdNameObj(this.id,this.name,this.isLeaf);
    }

    Boolean isLeaf;

    @PreUpdate
    @PrePersist
    public void preUpdateAndPersist(){
        if(this.subMenus!=null
                && this.subMenus.size()>0)
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
    SysMenu parent;

    @DirtField(title = "子目录")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    Set<SysMenu> subMenus;



    /** 菜单名称 */
    private String menuName;

    /** 父菜单名称 */
    private String parentName;



    /** 显示顺序 */
    private Integer orderNum;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否为外链（0是 1否） */
    private String isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    private String isCache;

    /** 类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 显示状态（0显示 1隐藏） */
    private String visible;

    /** 菜单状态（0显示 1隐藏） */
    private String status;

    /** 权限字符串 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    @Override
    public String genCode() {
        return "Menu_"+getSnowId();
    }
}
