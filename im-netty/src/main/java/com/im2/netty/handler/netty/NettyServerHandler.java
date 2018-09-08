package com.im2.netty.handler.netty;

import com.im2.common.protobuf.MessageTemplate;
import com.im2.common.protobuf.MessageTemplateWrapper;
import com.im2.netty.handler.ChartHandler;
import com.im2.netty.handler.LoginHandler;
import com.im2.netty.handler.storage.ChannelStrorage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.im2.common.protobuf.MessageTemplate.ActionType.LOGIN;
import static com.im2.common.protobuf.MessageTemplate.ActionType.PERSON_MESSAGE;
import static com.im2.common.protobuf.MessageTemplate.Status.SUCCESS;


/**
 * Created by liuyan on 2018/9/4.
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private ChartHandler chartHandler;
    @Autowired
    private ChannelStrorage channelStrorage;
    /**
     * 空闲次数
     */
    private Map<Channel, Integer> idle_count = new HashMap<>();

    /**
     * 建立连接时，发送一条消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接的客户端地址: {}", ctx.channel().remoteAddress());
        MessageTemplate.Message msg = MessageTemplate.Message.newBuilder().setStatus(SUCCESS).build();
        ctx.writeAndFlush(msg);
        super.channelActive(ctx);
    }

    /**
     * 超时处理 如果10秒没有接受客户端的心跳，就触发; 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.READER_IDLE.equals(event.state())) { // 如果读通道处于空闲状态，说明没有接收到心跳命令
                log.warn("已经10秒没有接收到客户端的信息了 channel: {}", ctx.channel().toString());
                if (idle_count.get(ctx.channel()) > 2) {
                    log.warn("关闭这个不活跃的channel channel: {}", ctx.channel().toString());
                    ctx.channel().close();
                    idle_count.remove(ctx.channel());
                    channelStrorage.removeChannel(ctx.channel());
                    return;
                }
                if (idle_count.get(ctx.channel()) == null) {
                    idle_count.put(ctx.channel(), 0);
                } else {
                    idle_count.put(ctx.channel(), idle_count.get(ctx.channel()) + 1);
                }
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("业务逻辑处理 :{}", msg);
        try {
            if (msg instanceof MessageTemplate.Message) {
                MessageTemplateWrapper msgWapper = new MessageTemplateWrapper((MessageTemplate.Message) msg, ctx.channel());
                int number = ((MessageTemplate.Message) msg).getBodyCase().getNumber();
                //登录
                if (LOGIN.getNumber() == number) {
                    loginHandler.deal(msgWapper);
                }
                //个人聊天
                if (PERSON_MESSAGE.getNumber() == number) {
                    chartHandler.ChartOneOne(msgWapper);
                }


            } else {
                log.error("未知数据 msg: {}", msg);
                return;
            }
        } catch (Exception e) {
            log.error("业务逻辑有误 msg: {}", msg);
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
