package com.zk.infrastructure;


import com.zk.domain.core.vo.Driver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ddd-uc")
public interface UserRibbonHystrixApi {
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserRibbonHystrixApi.class);
//    @Autowired
//    private RestTemplate restTemplate;
//
//    /**
//     * 使用@HystrixCommand注解指定当该方法发生异常时调用的方法
//     *
//     * @param id customerId
//     * @return 通过id查询到的用户
//     */

    @GetMapping("/users/{id}")
    Driver findById(@PathVariable("id") Long id);
    //    {
    //    Driver driver = this.restTemplate.getForObject("http://QBIKE-UC/users/" + id, Driver.class);
    //    driver.setDriverId(id);
    //    return driver;
    //}

    /**
     * hystrix fallback方法
     *
     * @param id customerId
     * @return 默认的用户
     */
    //public Driver fallback(Integer id) {
    //    UserRibbonHystrixApi.LOGGER.info("异常发生，进入fallback方法，接收的参数：customerId = {}", id);
    //    Driver driver = new Driver();
    //    driver.setDriverId(-1L);
    //    driver.setUserName("default driver");
    //    driver.setMobile("0000");
    //    return driver;
    //}
}
