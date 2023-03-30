package com.bange.netty.boundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class ByteToLongEncoder2 extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf byteBuf) throws Exception {
        System.out.println("ByteToLongEncoder的encode方法被调用");
        byteBuf.writeLong(msg);
    }
}
