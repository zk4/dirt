package com.zk;


import com.zk.dirt.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.zk.utils.StartUtils.logApplicationStartup;


@SpringBootApplication
@EnableAsync
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MemberApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);
        logApplicationStartup(run);
    }

}
