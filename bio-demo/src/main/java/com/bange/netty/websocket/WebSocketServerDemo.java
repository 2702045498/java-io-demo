package com.bange.netty.websocket;

import com.bange.netty.heartbeat.NettyServerHeartBeatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class WebSocketServerDemo {
    public static void main(String[] args) {
        EventLoopGroup boosGroup=null;
        EventLoopGroup workerGroup=null;
        try {
            boosGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 基于http协议，需要使用http的编码解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块的方式写
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 分段传输，对多段进行聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * (1)对应websocket,数据是帧(frame)的形式传递
                             * (2)ws://localhost:9990:/xxx为请求的uri
                             * (3)WebSocketServerProtocolHandler是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 业务逻辑handler
                            pipeline.addLast("webSocketServerHandler",new WebSocketServerHandler());

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
