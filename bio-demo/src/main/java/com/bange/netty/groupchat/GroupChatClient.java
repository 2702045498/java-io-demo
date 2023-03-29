package com.bange.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {

    private final static String HOST="127.0.0.1";
    private final static int PORT=9990;

  public void run(){
      NioEventLoopGroup eventExecutors=null;
      try {
           eventExecutors = new NioEventLoopGroup();
          Bootstrap bootstrap = new Bootstrap();
          bootstrap.group(eventExecutors)
                  .channel(NioSocketChannel.class)
                  .handler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      protected void initChannel(SocketChannel socketChannel) throws Exception {
                          ChannelPipeline pipeline = socketChannel.pipeline();
                          pipeline.addLast("decoder",new StringDecoder());
                          pipeline.addLast("encoder",new StringEncoder());
                          pipeline.addLast("groupChatClientHandler",new GroupChatClientHandler());
                      }
                  });
          ChannelFuture channelFuture = bootstrap.connect(HOST, PORT);
          Channel channel = channelFuture.channel();
          System.out.println("----------"+channel.localAddress()+"-----------");
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNext()){
              String msg = scanner.nextLine();
              channel.writeAndFlush(msg+"\r\n");
          }

      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
      } finally {
          System.out.println("=ss==>");
          eventExecutors.shutdownGracefully();
      }
  }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();
        groupChatClient.run();
    }
}
