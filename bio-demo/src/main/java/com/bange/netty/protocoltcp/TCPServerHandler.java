package com.bange.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 修改为自定义的解码器 SimpleChannelInboundHandler<ByteBuf>
 */
public class TCPServerHandler  extends SimpleChannelInboundHandler<MessageProtocol> {
    Logger logger = LoggerFactory.getLogger(TCPClientHandler.class);
    private int count=0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        logger.info("==>服务端接收到信息：");
        logger.info("信息长度："+len);
        logger.info("信息内容："+new String(content,Charset.forName("utf-8")));
        count++;
        logger.info("服务器接收到消息包量："+count);
        // 回复消息
        String responseContent=UUID.randomUUID().toString();
        int length = responseContent.length();
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(responseContent.getBytes(Charset.forName("utf-8")));
        messageProtocol.setLen(length);
        ctx.writeAndFlush(messageProtocol);
    }
//    // 服务端接收数据
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//      byte[] buffer=new byte[msg.readableBytes()];
//         msg.readBytes(buffer);
//        String message = new String(buffer, Charset.forName("utf-8"));
//        System.out.println("服务端接收消息："+message);
//        System.out.println("服务端接收量："+count++);
//
//        // 回显数据给客户端
//        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
//        ctx.writeAndFlush(byteBuf);
//
//    }
}
