package com.zk.intention.controller;

import com.zk.intention.entity.vo.Customer;
import com.zk.intention.entity.vo.DriverStatusVo;
import com.zk.intention.service.IntentionService;
import com.zk.intention.third.api.PositionApi;
import com.zk.intention.third.api.UserRibbonHystrixApi;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(tags = {"意向"})
public class IntentionController {
    @Autowired
    private UserRibbonHystrixApi userRibbonHystrixApi;
    @Autowired
    private IntentionService intentionService;
    @Autowired
    private PositionApi positionApi;

    @GetMapping("/ribbon/{id}")
    public Customer findById(@PathVariable Long id) {
        return this.userRibbonHystrixApi.findById(id);
    }

    @GetMapping("/ribbon/match")
    public Collection<DriverStatusVo> match(double longitude, double latitude) {
        return this.positionApi.match(longitude, latitude);
    }

    @PostMapping("/intentions/place")
    public void place(@RequestBody
                              MyIntention myIntention) {
        intentionService.placeIntention(myIntention.getUserId(),
                myIntention.getStartLongitude(), myIntention.getStartLatitude(),
                myIntention.getDestLongitude(), myIntention.getDestLatitude());
    }

    @PostMapping("/intention/confirm")
    public boolean confirm(Long driverId, Long intentionId) {
        return intentionService.confirmIntention(driverId, intentionId);
    }

    @Data
    public static class MyIntention {
        private Long userId;
        private Double startLongitude;
        private Double startLatitude;
        private Double destLongitude;
        private Double destLatitude;

    }

}
