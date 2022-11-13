package com.zk.ddd.entity.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_candidate")
@DirtEntity("候选人")
public class Candidate extends DirtSimpleIdEntity {
    @ManyToOne
    @JoinColumn(name = "itention_id")
    @DirtField
    private Intention intention;
    //
    //@Embedded
    //private Driver driver;
    @DirtField
    private String driverName;
    @DirtField
    private String driverMobile;

    @Embedded
    @DirtField
    @AttributeOverrides({
            @AttributeOverride(name="longitude",column=@Column(name="startLongitude")),
            @AttributeOverride(name="latitude",column=@Column(name="startLatitude")),
            @AttributeOverride(name="gender",column=@Column(name="startGender"))
    })
    Location startLocaiton;

    @Embedded
    @DirtField
    @AttributeOverrides({
            @AttributeOverride(name="longitude",column=@Column(name="destLongitude")),
            @AttributeOverride(name="latitude",column=@Column(name="destLatitude")),
            @AttributeOverride(name="gender",column=@Column(name="destGender"))

    })
    Location destLocation;
}
