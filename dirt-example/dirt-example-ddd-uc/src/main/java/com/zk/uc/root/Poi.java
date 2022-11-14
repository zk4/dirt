package com.zk.uc.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_poi")
@Setter
@Getter
@DirtEntity(value = "poi")
public class Poi extends DirtSimpleIdEntity {

    @Column(length = 64)
    @DirtField
    private String linkMan;
    @Column(length = 64)
    @DirtField
    private String shopName;
    @Column(length = 64, nullable = false)
    @DirtField
    private String cellPhone;
    @DirtField
    private Double longitude;
    @DirtField
    private Double latitude;
    @Column(length = 64)
    @DirtField
    private String province;
    @Column(length = 64)
    @DirtField
    private String city;
    @Column(length = 64)
    @DirtField
    private String district;
    @DirtField
    private String street;
    @DirtField
    private String streetNumber;
    @DirtField
    private int shopType;
    @DirtField
    private String userCode;
    @DirtField
    private String originAddress;

}
