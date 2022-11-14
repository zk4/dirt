package com.zk.infrastructure;


import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ddd-uc")
public interface UserRibbonHystrixApi {

    @Data
    public static class DriverDTO {

       Long id;

       String userName;

       String mobile;

       String type;

    }
    @GetMapping("/users/{id}")
    DriverDTO findById(@PathVariable("id") Long id);

}
