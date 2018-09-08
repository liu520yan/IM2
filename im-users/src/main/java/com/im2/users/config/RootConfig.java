package com.im2.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by liuyan on 2018/9/7.
 */
@Configuration
public class RootConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
