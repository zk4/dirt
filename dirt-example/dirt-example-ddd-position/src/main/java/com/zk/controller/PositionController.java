package com.zk.controller;


import com.zk.domain.core.root.DriverStatus;
import com.zk.domain.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api( tags = {"位置服务"})
public class PositionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);
    @Autowired
    PositionService positionService;

    @ApiOperation(value = "司机更新位置信息")
    @GetMapping("/trips/updatePosition")
    public void positionUpdate(Long driverId, Double longitude, Double latitude) {
        LOGGER.info(String.format("update position %s %s %s", driverId, longitude, latitude));
        positionService.updatePosition(driverId, longitude, latitude);
    }
    @ApiOperation(value = "匹配司机")
    @GetMapping("/trips/match")
    public Collection<DriverStatus> match(@RequestParam("longitude") Double longitude,@RequestParam("latitude") Double latitude) {
        return positionService.matchDriver(longitude, latitude);
    }
}
