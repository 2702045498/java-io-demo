package com.bange.netty.dubborpc.rpcclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Handler进行回调时，需要进行同步锁(synchronized),否则会抛出空异常
 */
public class RPCNettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    Logger logger = LoggerFactory.getLogger(Object.class);
    private ChannelHandlerContext context;
    private String result; // 调用返回结果
    private String param; // 客户端调用时传递的参数


    // 与服务器连接创建后就会调用 （1）
    @Override
    public  synchronized void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("1.调用channelActive");
        context = ctx;
    }
// 读取数据（4）
    @Override
    public synchronized   void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("4.调用channelRead："+msg.toString());
        result=msg.toString();
        notify();// 唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器=>wait=>等待被唤醒(channelRead)=>返回结果（3）
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        logger.info("3.调用call()：");
        // channelActive先被调用
        context.writeAndFlush(param);
        // 等待channelRead（）方法获取结果后进行唤醒
        wait();
        return result;
    }
    // 设置参数 （2）
    public void setParam(String param) {
        logger.info("2.调用setParam()：");
        this.param = param;
    }
}
