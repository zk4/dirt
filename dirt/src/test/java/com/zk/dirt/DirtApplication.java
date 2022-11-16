package com.zk.dirt;


import com.zk.dirt.annotation.DirtScan;
import com.zk.dirt.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;



//@SpringBootApplication
//@EnableAsync
//public class MemberApplication {
//    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(MemberApplication.class);
//        ConfigurableApplicationContext run = app.run(args);
//        SpringUtil.setApplicationContext(run);
//        Environment env = run.getEnvironment();
//        logApplicationStartup(env);
//    }
//
//}

@SpringBootApplication
@Slf4j
@EnableAsync
@DirtScan({"com.zk.dirt"})
public class DirtApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DirtApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);

    }


}
