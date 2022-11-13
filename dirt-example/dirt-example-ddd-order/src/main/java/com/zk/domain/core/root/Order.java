package com.zk.domain.core.root;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.domain.core.vo.CustomerVo;
import com.zk.domain.core.vo.DriverVo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;



@Getter
@Setter
@Entity
@Table(name = "t_qbike_order")
@DirtEntity("订单")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Order.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Order extends DirtSimpleIdEntity {

    @DirtField(title = "客户")
    @Embedded
    private CustomerVo customer;
    @DirtField(title = "司机")
    @Embedded
    private DriverVo driver;
    @DirtField
    private Double startLong;
    @DirtField
    private Double startLat;
    @DirtField
    private Double destLong;
    @DirtField
    private Double destLat;
    @DirtField
    @Temporal(TemporalType.TIMESTAMP)
    private Date opened;
    @DirtField
    private String orderStatus;
    @DirtField
    private Long intentionId;
}
