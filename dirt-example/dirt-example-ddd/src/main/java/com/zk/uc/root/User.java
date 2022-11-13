package com.zk.uc.root;

import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.uc.Type;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "T_USER")
@Data
public class User extends DirtSimpleIdEntity {

    @Column(length = 64)
    private String userName;
    @Column(length = 64, nullable = false)
    private String mobile;
    @Column(length = 64)
    private String province;
    @Column(length = 64)
    private String city;
    @Column(length = 64)
    private String district;
    private String street;
    private String originAddress;


    @Enumerated(value = STRING)
    @Column(length = 32, nullable = false)
    private Type type;

}
