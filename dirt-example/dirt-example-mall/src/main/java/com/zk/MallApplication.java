package com.zk;


import com.zk.dirt.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.zk.utils.StartUtils.logApplicationStartup;


@SpringBootApplication
@EnableAsync
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MallApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);
        Environment env = run.getEnvironment();
        logApplicationStartup(env);
    }
}
