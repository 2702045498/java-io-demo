package com.bange.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * 1. SimpleChannelInboundHandler<ByteBuf> 需要修改按照自定义协议包
 * 1.1 SimpleChannelInboundHandler<MessageProtocol>
 */
public class TCPClientHandler  extends SimpleChannelInboundHandler<MessageProtocol> {
    Logger logger = LoggerFactory.getLogger(TCPClientHandler.class);
    private int count=0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        logger.info("==>客户端接收消息："+new String(content,Charset.forName("utf-8")));
        count++;
        logger.info("==>客户端消息数量："+count);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                // 使用客户端发送10条数据
        for (int i = 0; i < 100; i++) {
          String msg="今天一定发送完1条数据"+i;
            byte[] content = msg.getBytes(Charset.forName("utf-8"));
            int length = content.length;
            // 创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        byte[] buffer = new byte[msg.readableBytes()];
//        msg.readBytes(buffer);
//        String message = new String(buffer, Charset.forName("utf-8"));
//        System.out.println("客户端接收到消息："+message);
//        System.out.println("客户端接收到消息数量："+count++);
//    }


//    // 发送数据
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        // 使用客户端发送10条数据
//        for (int i = 0; i < 100; i++) {
//            ByteBuf byteBuf = Unpooled.copiedBuffer("hello-server-" + i+"\t", Charset.forName("utf-8"));
//            ctx.writeAndFlush(byteBuf);
//        }
//
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      logger.info("==>异常消息："+cause.getMessage());
        ctx.close();
    }


}
