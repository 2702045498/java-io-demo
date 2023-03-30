package com.bange.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 协议包编码
 */
public class MessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {
    Logger logger = LoggerFactory.getLogger(MessageProtocolEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        logger.info("==>MessageProtocolEncoder的encode方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
