package com.zk.upms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@DirtEntity("角色")
@DynamicUpdate
@DynamicInsert
@Table(name = "upms_sys_role")
@SQLDelete(sql = "UPDATE upms_sys_role SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysRole.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SysRole extends MyBaseIdEntity {
    /**
     * 角色名称
     */
    @DirtField(title = "角色名称")
    private String roleName;

    /**
     * 角色权限
     */
    @DirtField(title = "角色权限")
    private String roleKey;

    /**
     * 角色排序
     */
    @DirtField(title = "角色排序")
    private Integer roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    @DirtField(title = "数据范围")
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @DirtField(title = "菜单树选择项是否关联显示")
    private boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    @DirtField(title = "部门树选择项是否关联显示")
    private boolean deptCheckStrictly;


    /**
     * 菜单组
     */
    @DirtField(title = "菜单组")
    @OneToMany
    // 允许双向更新
    @JoinTable(name = "upms_role_menu_rel",
            joinColumns = {@JoinColumn(name = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "menuId")})
    @JsonIdentityReference(alwaysAsId = true)
    private Set<SysMenu> menus;

    /**
     * 部门组（数据权限）
     */
    @DirtField(title = "部门组")
    @ManyToMany
    // 允许双向更新
    @JoinTable(name = "upms_role_dept_rel",
            joinColumns = {@JoinColumn(name = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "deptId")})
    @JsonIdentityReference(alwaysAsId = true)
    private Set<SysDept> depts;

    /** 角色菜单权限 */
    //@DirtField(title = "角色菜单权限")
    //@ManyToMany
    //// 允许双向更新
    //@JoinTable(name="upms_user_role_rel",
    //        joinColumns={@JoinColumn(name="roleId")},
    //        inverseJoinColumns={@JoinColumn(name="roleId")})
    //@JsonIdentityReference(alwaysAsId = true)
    //private Set<String> permissions;
}
