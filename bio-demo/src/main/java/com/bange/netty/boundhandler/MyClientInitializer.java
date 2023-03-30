package com.bange.netty.boundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("1.客户端编码");
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 加一个handler,对出站数据进行编码
        pipeline.addLast("encoder",new ByteToLongEncoder());
//        pipeline.addLast("decoder",new ByteToLongDecoder());
        pipeline.addLast("decoder",new ByteToLongDecoder2());

        // 出站handler处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
