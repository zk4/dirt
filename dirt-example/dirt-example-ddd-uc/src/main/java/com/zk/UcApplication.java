package com.zk;


import com.zk.dirt.annotation.DirtScan;
import com.zk.dirt.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Slf4j
@EnableAsync
@DirtScan({"com.zk.uc.root"})
@EntityScan({"com.zk.uc.root"})
@EnableDiscoveryClient

public class UcApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UcApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);

    }


}
