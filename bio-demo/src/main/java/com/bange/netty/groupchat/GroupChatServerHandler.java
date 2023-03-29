package com.bange.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 实现读方法
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // channel组管理所有的channel(全局事件执行器-单例)
    private static ChannelGroup channerGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 表示建立连接，一旦建立连接，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 将该客户端加入聊天推送给所有的客户端
        Channel channel = ctx.channel();
        // 遍历所有的channel发送消息给客户端
        channerGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天");
        channerGroup.add(channel);
    }
    // 表示channel处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端]"+ctx.channel().remoteAddress()+"上线了");
    }
    // 表示channel处于不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端]"+ctx.channel().remoteAddress()+"离线了");
    }
    // 断开连接 某客户端离开信息推送给所有客户端
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channerGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天"+sdf.format(new Date()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channerGroup.forEach(ch -> {
            if(channel!=ch){
                ch.writeAndFlush("[客户端]" + channel.remoteAddress() + "发送消息："+msg);
            }else {
                ch.writeAndFlush("[自己]" + channel.remoteAddress() + "发送消息："+msg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
