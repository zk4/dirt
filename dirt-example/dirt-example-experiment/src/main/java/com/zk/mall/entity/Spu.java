package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSearch;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DirtEntity("Spu")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_spu")
@SQLDelete(sql = "UPDATE mall_spu SET deleted = true WHERE id=?   and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@JsonIdentityInfo(scope = Spu.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Spu extends DirtBaseIdEntity {



    /**
     * 商品名称
     */
    @DirtField(title = "商品名称")
    private String name;
    /**
     * 商品描述
     */
    @DirtField(title = "商品描述")

    private String spuDescription;
    /**
     * 所属分类id
     */
    @ManyToOne
    @DirtField(title =  "所属分类",subTreeName = "subCategories", uiType = eUIType.cascader,dirtSearch = @DirtSearch(uiType = eUIType.cascader))
    @JsonIdentityReference(alwaysAsId = true)
    private Category  category;
    /**
     * 品牌id
     */
    @ManyToOne
    @DirtField
    @JsonIdentityReference(alwaysAsId = true)
    private Brand brand;

    /**
     * 品牌名
     */
    //@TableField(exist = false)
    //private String brandName;
    //
    //@DirtField(title = "品牌名")
    //private BigDecimal weight;
    /**
     * 商品介绍
     */
    @DirtField(title = "商品介绍")
    private String decript;

    @DirtSearch(title = "上架状态")
    private Boolean publishStatus;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

    @DirtField(title = "状态")
    Boolean status ;

    @DirtField(title = "实名")
    Boolean binding= true;

    @DirtAction(text = "状态改变")
    public void changeStatus() {

        if (this.status==null)
            this.status = false;
        this.status = !this.status;
    }


    @DirtAction(text = "切换绑定")
    public void bind() {
        if (this.binding==null)
            this.binding = false;
        this.binding = !this.binding;
    }

}
