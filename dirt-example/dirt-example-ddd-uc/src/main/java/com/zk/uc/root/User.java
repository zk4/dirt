package com.zk.uc.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.uc.eUserType;
import lombok.Getter;
import lombok.Setter;

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

    @DirtField
    private String mobile;

    @DirtField
    private String province;

    @DirtField
    private String city;

    @DirtField
    private String district;
    @DirtField
    private String street;
    @DirtField
    private String originAddress;

    @Enumerated(value = STRING)
    @DirtField
    private eUserType type;

}
