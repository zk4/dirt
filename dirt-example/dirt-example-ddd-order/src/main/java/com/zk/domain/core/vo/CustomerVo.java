package com.zk.domain.core.vo;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@DirtEntity(visiable = false)
public class CustomerVo {
    @DirtField
    private Long customerId;
    @DirtField
    private String customerName;
    @DirtField
    private String customerMobile;
}
