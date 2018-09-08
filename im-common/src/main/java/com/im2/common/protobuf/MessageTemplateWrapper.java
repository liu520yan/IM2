package com.im2.common.protobuf;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * Created by liuyan on 2018/9/5.
 */
public class MessageTemplateWrapper implements Serializable {
    public MessageTemplateWrapper(MessageTemplate.Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
    }

    private MessageTemplate.Message message;
    private Channel channel;

    public MessageTemplate.Message getMessage() {
        return message;
    }

    public void setMessage(MessageTemplate.Message message) {
        this.message = message;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
