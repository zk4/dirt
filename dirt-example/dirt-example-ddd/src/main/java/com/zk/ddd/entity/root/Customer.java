package com.zk.ddd.entity.root;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Customer  {

    private String customerName;
    private String customerMobile;
    private String userType;
}
