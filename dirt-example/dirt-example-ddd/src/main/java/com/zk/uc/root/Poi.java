package com.zk.uc.root;

import com.zk.dirt.entity.DirtSimpleIdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_poi")
@Data
public class Poi extends DirtSimpleIdEntity {

    @Column(length = 64)
    private String linkMan;
    @Column(length = 64)
    private String shopName;
    @Column(length = 64, nullable = false)
    private String cellPhone;
    private Double longitude;
    private Double latitude;
    @Column(length = 64)
    private String province;
    @Column(length = 64)
    private String city;
    @Column(length = 64)
    private String district;
    private String street;
    private String streetNumber;
    private int shopType;
    private String userCode;
    private String originAddress;

}
