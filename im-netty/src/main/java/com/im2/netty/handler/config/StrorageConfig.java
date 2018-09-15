package com.im2.netty.handler.config;

import com.im2.netty.handler.storage.ChannelStrorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * key-->channel映射配置类
 * Created by liuyan on 2018/9/8.
 */
@Configuration
public class StrorageConfig {
    @Bean
    public ChannelStrorage channelStrorage() {
        return ChannelStrorage.getInstance();
    }
}
