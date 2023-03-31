package com.bange.netty.dubborpc.rpcserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RPCNettyServer {
  private static   Logger logger = LoggerFactory.getLogger(Object.class);
    public static void startRpcServer(String host,int port){
        startRpcServer0(host,port);
    }

    // 初始化
    public static void startRpcServer0(String host, int port) {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workGroup = null;

        try {
            bossGroup = new NioEventLoopGroup(1);
            workGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new RPCNettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();
            logger.info("服务端开始启动……………………");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
