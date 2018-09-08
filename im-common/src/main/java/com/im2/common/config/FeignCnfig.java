package com.im2.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by liuyan on 2018/9/6.
 */
//@Service
public class FeignCnfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization","Basic YWNtZTphY21lc2VjcmV0");
    }
}
