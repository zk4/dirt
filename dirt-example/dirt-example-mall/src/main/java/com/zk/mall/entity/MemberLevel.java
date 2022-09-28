package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DirtEntity("等级")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_member_level")
@SQLDelete(sql = "UPDATE mall_member_level SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = MemberLevel.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class MemberLevel extends DirtBaseIdEntity {


    @DirtField(title = "等级名称")
    @NotEmpty
    @Size(max = 30)
    private String name;

    @DirtField(title = "等级需要的成长值")
    private Integer growthPoint;

    @DirtField(title = "是否为默认等级",tooltip = "0->不是；1->是")
    private Boolean defaultStatus;

    @DirtField(title = "免运费标准")
    private BigDecimal freeFreightPoint;

    @DirtField(title = "每次评价获取的成长值")
    private Integer commentGrowthPoint;

    /**
     * 是否有免邮特权
     */
    @DirtField(title = "是否有免邮特权")
    private Boolean priviledgeFreeFreight;
    /**
     * 是否有会员价格特权
     */
    @DirtField(title = "是否有会员价格特权")
    private Boolean priviledgeMemberPrice;
    /**
     * 是否有生日特权
     */
    @DirtField(title = "是否有生日特权")
    private Boolean priviledgeBirthday;
    /**
     * 备注
     */
    @DirtField(title = "备注",uiType = eUIType.textarea)
    private String note;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

}
