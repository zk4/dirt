package com.zk.dirt.spring.feign.sample;

import com.zk.dirt.EnableFeignProxies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignProxies(basePackages = "com.zk.dirt")
@ComponentScan("com.zk.dirt")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
