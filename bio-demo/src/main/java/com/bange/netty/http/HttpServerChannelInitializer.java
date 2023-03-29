package com.bange.netty.http;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // HttpServerCodec 是netty提供的编码解码器
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        pipeline.addLast("HttpServerHandler",new HttpServerHandler());
    }
}
