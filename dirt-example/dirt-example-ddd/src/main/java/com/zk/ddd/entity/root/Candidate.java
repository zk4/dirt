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

    @Embedded
    private Driver driver;
    @DirtField
    private String driverName;
    @DirtField
    private String driverMobile;
    @Embedded
    @DirtField
    //@Delegate
    Location location;

}
