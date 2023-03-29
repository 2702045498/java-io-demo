package com.bange.netty.protobuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;


/**
 * 1.自定义一个处理器，继承netty的HandlerAdapter
 * 2.需要实现一些规范，才是一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取实际数据，可以读取客户端发送的消息
     * @param ctx 上下文对象，含有管道pipeline,通道channel,地址等，同时pipeline包含channel，channel也包含pipeline
     * @param msg 客户端发送的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StudentPOJO.Student student  = (StudentPOJO.Student)msg;
        System.out.println("客户端发送的数据："+student.getId()+"--"+student.getName());

        // 1.如果在执行该方法时，非常耗时，那么就需要异步执行，提交该Channel对应的NioEventLoop的taskQueue中
        // 2.解决方案1：自定义普通任务
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10*1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                ((ByteBuf)msg).toString(StandardCharsets.UTF_8);
//            }
//        });

// 3.自定义提交到scheduledtaskQueue中
//        ctx.channel().eventLoop().schedule(new Runnable() {
//            @Override
//            public void run() {
//                ((ByteBuf) msg).toString(StandardCharsets.UTF_8);
//            }
//        },10*1000,TimeUnit.MILLISECONDS);



//        System.out.println("server ctx: "+ctx);
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息："+byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 读取数据完成，返回给客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",StandardCharsets.UTF_8));

    }
// 异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }


}
