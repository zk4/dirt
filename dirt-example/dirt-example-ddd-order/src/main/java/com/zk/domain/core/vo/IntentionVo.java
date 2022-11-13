package com.zk.domain.core.vo;

import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class IntentionVo {
    private Long customerId;
    private double startLong;
    private double startLat;
    private double destLong;
    private double destLat;
    private Long intentionId;
    private Long driverId;


}
