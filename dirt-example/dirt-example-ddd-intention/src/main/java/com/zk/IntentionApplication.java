package com.zk;


import com.zk.dirt.annotation.DirtScan;
import com.zk.dirt.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Slf4j
@EnableAsync
@DirtScan({"com.zk.intention.entity.root","com.zk.intention.entity.vo"})
@EntityScan({"com.zk.intention.entity.root","com.zk.intention.entity.vo"})
public class IntentionApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(IntentionApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);

    }


}
