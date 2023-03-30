package com.bange.netty.boundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 1.继承ReplayingDecoder解码器，不需要调用readablebytes()方法
 * 2.void不需要管理用户状态
 */
public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {
    /**
     *
     * @param ctx 上下文
     * @param byteBuf 入站的bytebuf
     * @param list 解码后的数据，传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 读取8个字节，因为long为8个字节
        System.out.println("ByteToLongDecoder2被调用");
            list.add(byteBuf.readLong());
    }
}
