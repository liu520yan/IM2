package com.im2.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by liuyan on 2017/12/7.
 */
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserApplication.class, args);
    }
}
