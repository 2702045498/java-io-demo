package com.bange.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 1.SimpleChannelInboundHandler 是ChannelInboundHandler的子类
 * 2.HttpObject客户端和服务器端通讯的数据被封装的类型
 */
public class HttpServerHandler  extends SimpleChannelInboundHandler<HttpObject> {
    // 读取客户端的信息
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        System.out.println("httpRequest类型："+httpObject.getClass());
        if(httpObject instanceof HttpRequest){
            System.out.println("httpRequest类型："+httpObject.getClass());
          HttpRequest httpRequest  =(HttpRequest)httpObject;
            URI  uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求的资源为/favicon.ico，不做处理");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("Hello World!你好啊", StandardCharsets.UTF_8);
            // 响应到http协议，使用httpResponse
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
//            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            // 响应头的长度
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            // 构建好的httpResponse返回
            channelHandlerContext.writeAndFlush(httpResponse);

        }
    }
}
