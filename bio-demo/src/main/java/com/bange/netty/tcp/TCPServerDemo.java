package com.bange.netty.tcp;


import com.bange.netty.boundhandler.MyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TCPServerDemo {
    public static void main(String[] args) {
        EventLoopGroup boosGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            boosGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TCPServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(9991).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
