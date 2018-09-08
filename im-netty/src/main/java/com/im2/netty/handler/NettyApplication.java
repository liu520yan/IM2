package com.im2.netty.handler;

import com.im2.netty.handler.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * Created by liuyan on 2017/12/7.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.im2.common.feignClient")
public class NettyApplication {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(NettyApplication.class, args);
        NettyServer nettyServer = context.getBean(NettyServer.class);
        nettyServer.run();
    }
}
