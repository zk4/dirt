package com.zk.intention.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

/**
 * 和Position服务中的DriverStatus内容一样
 * 这里的值对象的目的是避免共享带来的耦合
 * 让intention成为自治的服务
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DriverStatusVo {
    private int driverId;
    private Driver driver;
    private Double currentLongitude;
    private Double currentLatitude;
    private LocalDate updateTime;
}
