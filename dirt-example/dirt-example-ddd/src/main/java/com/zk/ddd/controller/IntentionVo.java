package com.zk.ddd.controller;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Data
@Accessors(fluent = false, chain = true)
public class IntentionVo {
    private Long customerId;
    private double startLong;
    private double startLat;
    private double destLong;
    private double destLat;
    private Long intentionId;
    private Long driverId;
}
