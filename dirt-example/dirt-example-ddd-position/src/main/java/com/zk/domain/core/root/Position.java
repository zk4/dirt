package com.zk.domain.core.root;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.domain.core.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;



@Getter
@Setter
@Entity
@Table(name = "t_position")
@DirtEntity("位置")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Position.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Position  extends DirtSimpleIdEntity {

    @DirtField
    private Double positionLongitude;
    @DirtField
    private Double positionLatitude;
    @Enumerated(value = STRING)

    @DirtField
    private Status status;
    @DirtField
    private String driverId;

}
