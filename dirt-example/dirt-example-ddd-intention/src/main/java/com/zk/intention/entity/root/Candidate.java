package com.zk.intention.entity.root;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.intention.entity.vo.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_candidate")
@DirtEntity("候选司机")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Candidate.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Candidate extends DirtSimpleIdEntity {
    @ManyToOne
    @JoinColumn(name = "itention_id")
    @DirtField
    private Intention intention;

    private int driverId;
    private String driverName;
    private String driverMobile;
    @Embedded
    @DirtField(title = "地址")
    private Location location;

}
