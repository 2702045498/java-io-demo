package com.bange.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 自定义协议解码器
 */
public class MessageProtocolDecoder  extends ReplayingDecoder<Void> {
    Logger logger = LoggerFactory.getLogger(MessageProtocolEncoder.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("==>MessageProtocolDecoder deecode 被调用");
        // 需要将读取到的二进制字节码转成MessageProtocol对象
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        // 封装成MessageProtocol对象，放入out,传给下一个Handler处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        out.add(messageProtocol);


    }
}
