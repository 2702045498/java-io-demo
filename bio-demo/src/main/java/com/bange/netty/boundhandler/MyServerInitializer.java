package com.bange.netty.boundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("3.服务端解码");
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 入站的Handler,解码器
//        pipeline.addLast("decoder",new ByteToLongDecoder());
        pipeline.addLast("decoder",new ByteToLongDecoder2());
        pipeline.addLast("encoder",new ByteToLongEncoder());
        // 业务处理器
        pipeline.addLast(new MyServerHandler());
    }
}
