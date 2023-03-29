package com.bange.netty.http;

import com.bange.netty.simple.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyHttpServer {
    public static void main(String[] args) {
        ChannelFuture channelFuture=null;
        EventLoopGroup boosGroup=null;
        EventLoopGroup workerGroup=null;
        try {
             boosGroup = new NioEventLoopGroup(1);
             workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new HttpServerChannelInitializer());
             channelFuture = serverBootstrap.bind("127.0.0.1",9988).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }
}
