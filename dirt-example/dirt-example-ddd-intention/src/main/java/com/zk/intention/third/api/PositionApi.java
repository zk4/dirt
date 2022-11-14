package com.zk.intention.third.api;

import com.zk.intention.entity.vo.DriverStatusVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Collection;

//@Service
@FeignClient(value = "ddd-position")
public interface PositionApi {
    //private static final Logger LOGGER = LoggerFactory.getLogger(PositionApi.class);
    //@Autowired
    //private RestTemplate restTemplate;

    //@HystrixCommand(fallbackMethod = "defaultMatch")
    @GetMapping("/trips/match")
    Collection<DriverStatusVo> match(@PathParam("RequestParam") Double longitude, @RequestParam("latitude") Double latitude);
    //{
    //    ResponseEntity<Collection<DriverStatusVo>> matchReponse =
    //            restTemplate.exchange(
    //                    String.format("http://qbike-trip/trips/match?longitude=%s&latitude=%s", longitude, latitude),
    //                    HttpMethod.GET, null,
    //                    new ParameterizedTypeReference<Collection<DriverStatusVo>>() {
    //                    }
    //            );
    //    return matchReponse.getBody();
    //}
    //
    //public Collection<eDriverStatus> defaultMatch(double longitude, double latitude) {
    //    return new ArrayList<>();
    //}
}
