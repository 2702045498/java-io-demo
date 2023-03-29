package com.bange.netty.protobufcode;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;


/**
 * 1.自定义一个处理器，继承netty的HandlerAdapter
 * 2.需要实现一些规范，才是一个Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    /**
     * 读取实际数据，可以读取客户端发送的消息
     * @param ctx 上下文对象，含有管道pipeline,通道channel,地址等，同时pipeline包含channel，channel也包含pipeline
     * @param msg 客户端发送的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 根据datatype显示不同信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType==MyDataInfo.MyMessage.DataType.studentType){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("studnet:id= "+student.getId()+"studnet:name= "+student.getName());
        } else if (dataType==MyDataInfo.MyMessage.DataType.workerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("work:age= "+worker.getAge()+"worker:name= "+worker.getName());
        }else{
            System.out.println("传输类型不正确");
        }
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
