package com.bange.netty.dubborpc.rpcclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCNettyClient {
  private static   Logger logger = LoggerFactory.getLogger(Object.class);
    // 线程跟系统核数一致
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static RPCNettyClientHandler rpcNettyClientHandler;


    public static void initClient() {
        rpcNettyClientHandler = new RPCNettyClientHandler();
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(rpcNettyClientHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9992).sync();
            logger.info("客户端准备启动………………");
            // 初始化后不能进行关闭
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用代理模式获取一个代理对象
     * @param serviceClass 服务类
     * @param providerName 信息前缀helloservice#helo#
     * @return
     */
    public Object getBean(final Class<?> serviceClass, String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},
                ((proxy, method, args) -> { // lamada表达式代替内部类 ,客户端调用hello()就会调用执行一次该代码
                    if (rpcNettyClientHandler == null) {
                        initClient();
                    }

                    // 设置信息发送给服务端:providerName消息前缀+args[0]为客户端调用api hello(???)参数
                    rpcNettyClientHandler.setParam(providerName + args[0]);
                    // 将rpcNettyClientHandler提交到线程池进行执行
                    return executor.submit(rpcNettyClientHandler).get();
                }));
    }
}
