package com.bange.netty.boundhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class BoundHandlerClientDemo {
    public static void main(String[] args) {
        EventLoopGroup group = null;
        try {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());// 自定义一个初始化类
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9991).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();

        }
    }
}
