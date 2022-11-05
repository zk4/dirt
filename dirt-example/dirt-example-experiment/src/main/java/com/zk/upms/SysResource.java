package com.zk.upms;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.intef.iEnumText;
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
@DirtEntity("资源")
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_resource")
@SQLDelete(sql = "UPDATE sys_resource SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SysResource.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public class SysResource extends MyBaseIdEntity {

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
        return new IdNameObj(this.id, this.name, this.isLeaf);
    }

    Boolean isLeaf;

    @PreUpdate
    @PrePersist
    public void preUpdateAndPersist() {
        if (this.subMenus != null
                && this.subMenus.size() > 0)
            isLeaf = false;
        else
            isLeaf = true;
    }

    @DirtField(title = "目录名")
    @NotEmpty
    @Size(max = 30)
    String name;

    @DirtField(title = "父目录")
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    SysResource parent;

    //@DirtField(title = "子目录")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIgnore
    Set<SysResource> subMenus;

    @DirtField(title = "显示顺序")
    private Integer orderNum;

    @DirtField(title = "路由key")
    private String routerKey;

    @DirtField(title = "是否为外链")
    private Boolean isFrame;


    enum eMenuType implements iEnumText {
        MENU("目录"),
        MENU_ITEM("子目录"),
        BTN("按钮");
        String text;
        eMenuType(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }
    @DirtField(title = "资源类型")
    private eMenuType menuType;

    @DirtField(title = "显示状态")
    private Boolean visible;

    @DirtField(title = "权限字符串")
    private String perms;

    @DirtField(title = "菜单图标")
    private String icon;

    @Override
    public String genCode() {
        return "Menu_" + getSnowId();
    }
}
