package com.bange.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientDemo {
    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = null;
        try {
            // 1.事件循环组
            eventLoopGroup = new NioEventLoopGroup();
            // 2.客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            // 2.1设置相关参数(设置线程组)
            bootstrap.group(eventLoopGroup)
                    // 2.2客户端实现类，通过反射获取
                    .channel(NioSocketChannel.class)
                    // 2.3处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        // 2.4初始化通道，重写父类方法，实现自定义处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端已经准备ok");
            // 3.分析netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9988).sync();
            // 4.关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
