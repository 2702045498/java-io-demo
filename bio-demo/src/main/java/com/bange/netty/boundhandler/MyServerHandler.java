package com.bange.netty.boundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("4.服务端读取数据");
        System.out.println("从客户端"+ctx.channel().remoteAddress()+"读取到一个long类型数据："+msg);
        // 写回客户端
        ctx.writeAndFlush(5678l);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
