package com.zk.domain.core.root;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.domain.core.Status;
import com.zk.domain.core.vo.Driver;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Table(name = "t_driver_status")
@DirtEntity("司机状态")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = DriverStatus.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class DriverStatus extends DirtSimpleIdEntity {
    @DirtField
    @Embedded
    private Driver driver;
    @DirtField
    private Double currentLongitude;
    @DirtField
    private Double currentLatitude;
    @Enumerated(value = STRING)
    @DirtField
    private Status status;

}
