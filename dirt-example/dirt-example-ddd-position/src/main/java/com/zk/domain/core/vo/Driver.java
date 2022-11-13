package com.zk.domain.core.vo;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@DirtEntity(visiable = false)
public class Driver {
    @DirtField
    private Long driverId;
    @DirtField
    private String userName;
    @DirtField
    private String mobile;
    @DirtField
    private String type;
}
