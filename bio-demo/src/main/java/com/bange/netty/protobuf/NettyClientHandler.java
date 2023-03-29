package com.bange.netty.protobuf;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.StandardCharsets;

public class NettyClientHandler  extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client ctx:"+ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("你好啊", StandardCharsets.UTF_8));

        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("小李广花荣").build();
        ctx.writeAndFlush(student);

    }

    /**
     * 通道有读取事件时触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息："+byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 处理异常信息
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        try {
            ctx.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
