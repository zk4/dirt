package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity(value = "分类")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_category")
@SQLDelete(sql = "UPDATE mall_category SET deleted = true WHERE id=? and version=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Category.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public class Category extends DirtBaseIdEntity {

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
        if(this.subCategories!=null
                && this.subCategories.size()>0)
            isLeaf = false;
        else
            isLeaf = true;
    }

    @DirtField(title = "层级" )
    @NotNull
    private Integer level;

    @DirtField(title = "图标地址" ,uiType = eUIType.imageUploader)
    private String icon;

    /**
     * 计量单位
     */
    @DirtField(title = "计量单位")
    private String productUnit;
    /**
     * 商品数量
     */
    @DirtField(title = "商品数量")
    private Integer productCount;


    @NotEmpty
    @Size(max = 30)
    @DirtField(title = "类名", uiType = eUIType.text)
    String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parentId")
    Category parent;

    @DirtField(title = "子类")
    @OneToMany
    @JoinColumn(name = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Category> subCategories;




    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
