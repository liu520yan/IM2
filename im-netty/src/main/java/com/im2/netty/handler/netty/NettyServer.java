package com.im2.netty.handler.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by liuyan on 2018/9/4.
 */
@Slf4j
@Service
public class NettyServer {

    @Autowired
    private NettyServerFilter nettyServerFilter;
    @Value("${netty.port}")
    private int port;

    private static EventLoopGroup boss = new NioEventLoopGroup(); // 通过nio方式来接收连接和处理连接
    private static EventLoopGroup work = new NioEventLoopGroup(); // 通过nio方式来接收连接和处理连接
    private static ServerBootstrap b = new ServerBootstrap();

    public void run() {
        try {
            b.group(boss, work);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(nettyServerFilter); // 设置过滤器
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            log.info("服务端启动成功,端口是:{}", port);
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
