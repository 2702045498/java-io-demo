package com.bange.netty.boundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("收到服务端的回复："+msg);
    }
    // 重写，发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("2.客户端准备发送数据");
        ctx.writeAndFlush(18882l); //发送long类型数据
    }
}
