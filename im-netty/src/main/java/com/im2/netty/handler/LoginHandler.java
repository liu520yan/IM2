package com.im2.netty.handler;


import com.im2.common.protobuf.MessageTemplateWrapper;

/**
 * Created by liuyan on 2018/9/5.
 */
public interface LoginHandler {
    void deal(MessageTemplateWrapper wapper);
}
