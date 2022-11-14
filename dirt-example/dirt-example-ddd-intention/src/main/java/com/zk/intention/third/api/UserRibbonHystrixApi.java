package com.zk.intention.third.api;

import com.zk.intention.entity.vo.Customer;
import com.zk.intention.entity.vo.Driver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ddd-uc")
public interface UserRibbonHystrixApi {


    /**
     * 使用@HystrixCommand注解指定当该方法发生异常时调用的方法
     *
     * @param id customerId
     * @return 通过id查询到的用户
     */
    @GetMapping("/users/{customerId}")
    Customer findById(@PathVariable("customerId") Long id);
        //return this.restTemplate.getForObject("http://QBIKE-UC/users/" + customerId, Customer.class);
    //    Map ret = restTemplate.getForObject("http://QBIKE-UC/users/" + id, Map.class);
    //    Customer customerVo = new Customer();
    //    customerVo.setCustomerId(id);
    //    customerVo.setCustomerMobile(String.valueOf(ret.get("mobile")));
    //    customerVo.setCustomerName(String.valueOf(ret.get("userName")));
    //    customerVo.setUserType(String.valueOf(ret.get("type")));
    //    return customerVo;
    //}

    @GetMapping("/users/{customerId}")
    Driver findDriverById(@PathVariable("customerId") Long id);
    //
    //Driver findDriverById(Long id) {
    //    Map ret = restTemplate.getForObject("http://localhost:8082/users/" + id, Map.class);
    //    Driver driverVo = new Driver();
    //    driverVo.setDriverId(id);
    //    driverVo.setMobile(String.valueOf(ret.get("mobile")));
    //    driverVo.setUserName(String.valueOf(ret.get("userName")));
    //    driverVo.setType(String.valueOf(ret.get("type")));
    //    return driverVo;
    //}

    /**
     * hystrix fallback方法
     *
     * @param id customerId
     * @return 默认的用户
     */
    //public Customer fallback(Integer id) {
    //    UserRibbonHystrixApi.LOGGER.info("异常发生，进入fallback方法，接收的参数：customerId = {}", id);
    //    Customer customer = new Customer();
    //    customer.setCustomerId(-1L);
    //    customer.setCustomerName("default username");
    //    customer.setCustomerMobile("0000");
    //    return customer;
    //}
}
