package com.zk;


import com.alibaba.druid.pool.DruidDataSource;
import com.zk.dirt.annotation.DirtScanPacakge;
import com.zk.dirt.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.zk.utils.StartUtils.logApplicationStartup;


@SpringBootApplication
@EnableAsync
@DirtScanPacakge({"com.zk"})
public class MallApplication {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MallApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        SpringUtil.setApplicationContext(run);
         logApplicationStartup(run);
        DruidDataSource bean = run.getBean(DruidDataSource.class);
        String dburl = (bean.getUrl());


    }

}
