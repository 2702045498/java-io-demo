package com.bange.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServerDemo {
    public static void main(String[] args) {
        EventLoopGroup boosGroup=null;
        EventLoopGroup workerGroup=null;
        try {
             boosGroup = new NioEventLoopGroup(1);
             workerGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /**
                             * IdleStateHandler 处理空闲状态的处理器
                             *long readerIdleTime：表示多长事件没有读操作，发送心跳检测包检测是否连接
                             * long writerIdleTime：表示多长事件没有写操作，发送心跳检测包检测是否连接
                             * long allIdleTime：表示多长事件没有读写操作，发送心跳检测包检测是否连接
                             * dleStateHandler触发后就会传递给pipeline管道的下一个handler处理，会调用下一个userEventTigger
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,6, TimeUnit.SECONDS));
                            // 对服务器端空闲自定义处理器
                            pipeline.addLast("nettyServerHeartBeatHandler",new NettyServerHeartBeatHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(9990).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
