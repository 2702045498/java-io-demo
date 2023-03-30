package com.bange.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


public class TCPServerHandler  extends SimpleChannelInboundHandler<ByteBuf> {
    private int count=0;
    // 服务端接收数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
      byte[] buffer=new byte[msg.readableBytes()];
         msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("服务端接收消息："+message);
        System.out.println("服务端接收量："+count++);

        // 回显数据给客户端
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.writeAndFlush(byteBuf);

    }
}
