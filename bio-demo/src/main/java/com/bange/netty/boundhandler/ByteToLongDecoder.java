package com.bange.netty.boundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToLongDecoder  extends ByteToMessageDecoder {
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
        if(byteBuf.readableBytes()>=8){
            list.add(byteBuf.readLong());
        }
    }
}
