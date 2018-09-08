package com.im2.netty.handler.imp;

import com.im2.common.protobuf.MessageTemplate;
import com.im2.common.protobuf.MessageTemplateWrapper;
import com.im2.netty.handler.ChartHandler;
import com.im2.netty.handler.storage.ChannelStrorage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.im2.common.protobuf.MessageTemplate.ActionType.PERSON_MESSAGE;
import static com.im2.common.protobuf.MessageTemplate.MessageSign.RESPONSE;
import static com.im2.common.protobuf.MessageTemplate.Status.SUCCESS;

/**
 * Created by liuyan on 2018/9/8.
 */
@Slf4j
@Service
public class ChartHandlerImpl implements ChartHandler {

    @Autowired
    private ChannelStrorage channelStrorage;

    @Override
    public void ChartOneOne(MessageTemplateWrapper msgWapper) {
        log.info("用户：{}，开始一对一聊天", msgWapper.getMessage().getUserSign());
        Channel channel = channelStrorage.getChannelByKey(msgWapper.getMessage().getPreInfo().getReceiver());
        if (channel != null) {
            //用户在线
            log.info("用户sign:{}在线", msgWapper.getMessage().getUserSign());
            MessageTemplate.PreInfo preInfo = MessageTemplate.PreInfo.newBuilder()
                    .setTime(System.currentTimeMillis())
                    .setSender(msgWapper.getMessage().getUserSign())
                    .setReceiver(msgWapper.getMessage().getPreInfo().getReceiver())
                    .setContent(msgWapper.getMessage().getPreInfo().getContent())
                    .build();
            MessageTemplate.Message msg = MessageTemplate.Message.newBuilder()
                    .setSign(RESPONSE)
                    .setType(PERSON_MESSAGE)
                    .setPreInfo(preInfo)
                    .setStatus(SUCCESS)
                    .build();
            channel.writeAndFlush(msg);
        } else {
            log.info("用户sign:{}离线", msgWapper.getMessage().getUserSign());
            //todo 用户离线
        }
    }
}
