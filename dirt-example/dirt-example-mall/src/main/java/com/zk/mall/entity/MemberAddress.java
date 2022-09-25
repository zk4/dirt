package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
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

@Getter
@Setter
@Entity
@DirtEntity("地址")
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_member_address")
@SQLDelete(sql = "UPDATE mall_member_address SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = MemberAddress.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MemberAddress extends DirtBaseIdEntity {

    @DirtField(title = "收货人姓名")
    private String name;
    /**
     * 电话
     */
    @DirtField(title = "电话")
    @NotEmpty
    @Size(max = 11)
    private String phone;

    @DirtField(title = "邮政编码")
    private String postCode;

    @DirtField(title = "省份/直辖市")
    private String province;

    @DirtField(title = "城市")
    private String city;

    @DirtField(title = "区")
    private String region;

    @DirtField(title = "详细地址(街道)")
    private String detailAddress;

    @DirtField(title = "省市区代码")
    private String areacode;

    @DirtField(title = "是否默认")
    private Boolean defaultStatus;


    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

}
