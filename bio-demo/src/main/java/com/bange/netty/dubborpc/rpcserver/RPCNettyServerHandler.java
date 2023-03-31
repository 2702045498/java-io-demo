package com.bange.netty.dubborpc.rpcserver;

import com.bange.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RPCNettyServerHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(Object.class);
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("==>服务端调用channelActive");
//        super.channelActive(ctx);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 读取客户端消息
            logger.info("客户端消息："+msg);
            // 自定义一个消息协议：必须以某个字符串开头
        String message = msg.toString();
        if(message.startsWith("helloservice#hello#")){
            String subMessage = message.substring(message.lastIndexOf("#") + 1);
            String result = new HelloServiceImpl().hello(subMessage);
            // 回显到客户端
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
