package com.zk;


import com.zk.dirt.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.zk.utils.StartUtils.logApplicationStartup;


@SpringBootApplication
@Slf4j
@EnableAsync
public class Template00Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Template00Application.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);
        Environment env = run.getEnvironment();
        logApplicationStartup(env);
    }
}
