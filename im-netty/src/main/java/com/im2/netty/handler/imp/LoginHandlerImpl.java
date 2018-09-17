package com.im2.netty.handler.imp;

import com.im2.common.feignClient.Oauth2Feign;
import com.im2.common.protobuf.MessageTemplate;
import com.im2.common.protobuf.MessageTemplateWrapper;
import com.im2.netty.handler.LoginHandler;
import com.im2.netty.handler.entity.OfflLineMsgEntity;
import com.im2.netty.handler.mapper.OfflineMsgMapper;
import com.im2.netty.handler.storage.ChannelStrorage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.im2.common.protobuf.MessageTemplate.ActionType.LOGIN;
import static com.im2.common.protobuf.MessageTemplate.ActionType.PERSON_MESSAGE;
import static com.im2.common.protobuf.MessageTemplate.MessageSign.RESPONSE;
import static com.im2.common.protobuf.MessageTemplate.Status.REQUEST_ERROR;
import static com.im2.common.protobuf.MessageTemplate.Status.SUCCESS;

/**
 * Created by liuyan on 2018/9/5.
 */
@Slf4j
@Service
public class LoginHandlerImpl implements LoginHandler {

    private static String base64Credentials;

    @Autowired
    private OfflineMsgMapper offlineMsgMapper;
    @Autowired
    private Oauth2Feign oauth2Feign;
    @Autowired
    private ChannelStrorage channelStrorage;

    @Value("${basic.client.username}")
    private String username;
    @Value("${basic.client.password}")
    private String password;

    @PostConstruct
    public void init() {
        Base64.Encoder encoder = Base64.getEncoder();
        String key = username + ":" + password;
        byte[] bytes = encoder.encode(key.getBytes());
        base64Credentials = "Basic " + new String(bytes);
        log.info("base64 :{}", base64Credentials);
    }

    @Override
    public void deal(MessageTemplateWrapper wrapper) {
        //校验Token
        Map<String, Object> map = oauth2Feign.checkToken(wrapper.getMessage().getToken(), base64Credentials);
        if (map.get("user_name") != null) {// todo 硬编码
            success(wrapper);
        } else {
            failed(wrapper);
        }
    }

    /**
     * 登录失败
     *
     * @param wrapper
     */
    private void failed(final MessageTemplateWrapper wrapper) {
        // 发送响应数据包
        MessageTemplate.Message message = MessageTemplate.Message.newBuilder().setSign(RESPONSE).setType(LOGIN).setStatus(REQUEST_ERROR).build();
        final ChannelFuture future = wrapper.getChannel().writeAndFlush(message);
        future.addListener((ChannelFutureListener) future1 -> {
            log.info("用戶：{} 登录失败", wrapper.getMessage().getAccount().getUsername());
            wrapper.getChannel().close().sync();
        });
    }

    /**
     * 响应登录成功业务逻辑处理
     *
     * @param wrapper
     */
    private void success(final MessageTemplateWrapper wrapper) {
        MessageTemplate.Message message = MessageTemplate.Message.newBuilder().setSign(RESPONSE).setType(LOGIN).setStatus(SUCCESS).build();
        final ChannelFuture future = wrapper.getChannel().writeAndFlush(message);
        future.addListener(
                (ChannelFutureListener) channelFuture -> {
                    if (future.isSuccess()) {
                        log.info("用戶：{} 登录成功", wrapper.getMessage().getAccount().getUsername());
                        channelStrorage.setKeyAndChannel(wrapper.getMessage().getAccount().getPhone(), wrapper.getChannel());
                        //发送个人离线信息
                        List<OfflLineMsgEntity> offlineMsgs = offlineMsgMapper.findEntityByAccept(wrapper.getMessage().getAccount().getPhone());
                        if (!CollectionUtils.isEmpty(offlineMsgs)) {
                            for (OfflLineMsgEntity entity : offlineMsgs) {
                                Channel channel = channelStrorage.getChannelByKey(entity.getAccept());
                                MessageTemplate.PreInfo preInfo = MessageTemplate.PreInfo.newBuilder()
                                        .setTime(entity.getTime())
                                        .setSender(entity.getSender())
                                        .setReceiver(entity.getAccept())
                                        .setContent(entity.getContent())
                                        .build();
                                MessageTemplate.Message msg = MessageTemplate.Message.newBuilder()
                                        .setSign(RESPONSE)
                                        .setType(PERSON_MESSAGE)
                                        .setPreInfo(preInfo)
                                        .setStatus(SUCCESS)
                                        .build();
                                channel.writeAndFlush(msg);
                                log.info("用户：{} 离线消息发送成功", wrapper.getMessage().getAccount().getPhone());
                            }

                        }

                        //todo 发送群组离线信息
                    } else {
                        log.warn("用戶：{} 登录失败", wrapper.getMessage().getAccount().getUsername());
                    }
                }
        );
    }
}