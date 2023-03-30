package com.bange.netty.tcp;

import io.netty.channel.ChannelInitializer;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class TCPServerInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("tcpServerHandler",new TCPServerHandler());
    }
}
