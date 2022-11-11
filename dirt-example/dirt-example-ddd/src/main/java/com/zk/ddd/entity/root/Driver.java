package com.zk.ddd.entity.root;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;


@Embeddable
@Setter
@Getter
public class Driver  {
    private Long driverId;
    private String userName;
    private String mobile;
    private String type;
}
