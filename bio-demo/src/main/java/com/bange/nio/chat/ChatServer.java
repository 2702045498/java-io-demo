package com.bange.nio.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChatServer {
    //选择器、通道、端口
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT=8899;

    public ChatServer() {
        try {
            selector=Selector.open();
            serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            //修改为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //把通道注册到选择器上，开始监听接收事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
      e.printStackTrace();
        }
    }
    //监听事件
    public void listen(){

        try {
            while (selector.select()>0){
                //获取选择器注册的通道的就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    SelectionKey key = it.next();
                    if(key.isAcceptable()){
                        //客户端接入请求，获取客户端通道
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()) {
                        //读取客户端通道信息，然后实现转发逻辑
                        readClientData(key);

                    }
                    //清空当前事件
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //读取客户端事件数据
    private void readClientData(SelectionKey key) {
        SocketChannel channel =null;
        try {
            //获取客户端通道
             channel =(SocketChannel) key.channel();
             //定义缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if(count>0){
                buffer.flip();
                String msg = new String(buffer.array(), 0, buffer.remaining());
                System.out.println("==>接收客户端："+msg);
                //把该消息转发给所有客户端
                sendMsgToAllClient(msg,channel);
            }


        } catch (Exception e) {
            try {
                System.out.println("有客户端离线："+channel.getRemoteAddress());
                key.cancel();
                channel.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
    //转发消息给所有客户端
    private void sendMsgToAllClient(String msg, SocketChannel channel) {
        try {
            System.out.println("服务端开始转发消息："+Thread.currentThread().getName());
            for (SelectionKey key:selector.selectedKeys()) {
                SocketChannel channel1 =(SocketChannel) key.channel();
                if(channel1 instanceof SocketChannel && channel1 != channel){
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    channel1.write(buffer);
                }
            }
//            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//            while (iterator.hasNext()){
//                SocketChannel channel1 =(SocketChannel) iterator.next().channel();
//                //判断不要把数据发送给自己
//                if(channel1 instanceof SocketChannel && channel1 != channel){
//                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
//                    channel1.write(buffer);
//                }
//            }

        } catch (IOException e) { e.printStackTrace();}

    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        //监听客户端的各种事件、连接、群聊、离线
        chatServer.listen();
    }

}
