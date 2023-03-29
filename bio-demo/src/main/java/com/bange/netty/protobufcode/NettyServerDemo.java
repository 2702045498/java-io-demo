package com.bange.netty.protobufcode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServerDemo {
    public static void main(String[] args)throws Exception {
        //1.创建两个线程
        /*
          (1) boosGroup和workGroup含有子线程(NioEventLoopGroup)的个数：CPU实际核数*2
          源码： private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
          (2) boosGroup核workGroup默认8个线程，当客户端数大于8时，workGroup会循环分配线程
         */

        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 2.创建服务器的启动对象，并设置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 2.1设置两个线程组
        serverBootstrap.group(boosGroup, workerGroup)
                // 2.2 设置NioSctpServerChannel作为服务器的通道实现
                .channel(NioServerSocketChannel.class)
                // 2.3 设置线程队列连接等待连接个数
                .option(ChannelOption.SO_BACKLOG, 128)
                // 2.4 设置连接保持活动状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 2.5 设置一个通道测试对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder",new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                        // 2.6给pipeline设置处理器handler
                        // pipeline底层是一个双向链表
                        pipeline.addLast(new NettyServerHandler());
                    }
                });

        System.out.println("服务器准备好了");
        // 3. 绑定一个端口，并同步，生成一个ChannelFuture对象(启动服务器)
        ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1",9988).sync();
        // 4.对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();



    }
}
