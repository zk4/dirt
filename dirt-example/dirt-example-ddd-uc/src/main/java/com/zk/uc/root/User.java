package com.zk.uc.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.uc.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "T_USER")
@Getter
@Setter
@DirtEntity(value = "用户")
public class User extends DirtSimpleIdEntity {

    @DirtField
    private String userName;
    @Column(length = 64)
    @DirtField
    private String mobile;
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
    private String originAddress;


    @Enumerated(value = STRING)
    @Column(length = 32, nullable = false)
    @DirtField
    private Type type;

}
