package com.bange.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class TCPClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {
    private int count=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端接收到消息："+message);
        System.out.println("客户端接收到消息数量："+count++);
    }
    // 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据
        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello-server-" + i+"\t", Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
