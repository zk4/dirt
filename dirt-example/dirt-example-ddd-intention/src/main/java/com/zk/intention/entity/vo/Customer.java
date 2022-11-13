package com.zk.intention.entity.vo;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
@DirtEntity(visiable = false)
public class Customer  {
    @DirtField(title = "乘客 id")
    private Long customerId;
    @DirtField(title = "名字")
    private String customerName;
    @DirtField(title = "手机")
    private String customerMobile;
    @DirtField(title = "类型")
    private String userType;
}
