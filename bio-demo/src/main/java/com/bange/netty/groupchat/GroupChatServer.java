package com.bange.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run(){
        EventLoopGroup boosGroup=null;
        EventLoopGroup workerGroup=null;
        ServerBootstrap serverBootstrap=null;
        ChannelFuture channelFuture=null;
        try {
            // 1.group线程组
             boosGroup = new NioEventLoopGroup(1);
             workerGroup = new NioEventLoopGroup();
            // 2.启动器
             serverBootstrap = new ServerBootstrap();
            // 2.1设置响应参数
            serverBootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            // 业务处理器
                            pipeline.addLast("myChatServerHandler",new GroupChatServerHandler());
                        }
                    });
            System.out.println("==>netty 服务器启动");
             channelFuture = serverBootstrap.bind("127.0.0.1",port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer(9990);
        groupChatServer.run();
    }

}
