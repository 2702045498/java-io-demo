package com.bange.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
// channel的初始化类
public class TCPClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 自定义协议编码
        pipeline.addLast("encoder",new MessageProtocolEncoder());
        pipeline.addLast("decoder",new MessageProtocolDecoder());
        pipeline.addLast("tcpClientHandler",new TCPClientHandler());
    }
}
