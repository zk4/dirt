package com.zk.intention.third.api;

import com.zk.intention.entity.vo.DriverStatusVo;
import com.zk.intention.types.eDriverStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class PositionApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionApi.class);
    @Autowired
    private RestTemplate restTemplate;

    //@HystrixCommand(fallbackMethod = "defaultMatch")
    public Collection<DriverStatusVo> match(double longitude, double latitude) {
        ResponseEntity<Collection<DriverStatusVo>> matchReponse =
                restTemplate.exchange(
                        String.format("http://qbike-trip/trips/match?longitude=%s&latitude=%s", longitude, latitude),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<Collection<DriverStatusVo>>() {
                        }
                );
        return matchReponse.getBody();
    }

    public Collection<eDriverStatus> defaultMatch(double longitude, double latitude) {
        return new ArrayList<>();
    }
}
