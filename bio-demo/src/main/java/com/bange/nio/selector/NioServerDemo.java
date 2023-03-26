package com.bange.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServerDemo {
    public static void main(String[] args) throws Exception{
        // 1.创建一个通道
        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();
        // 2.绑定地址,同时设置非阻塞
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",9999));
        serverSocketChannel.configureBlocking(false);
        // 3.获取selector对象
        Selector selector = Selector.open();
        // 4.将ServerSocketChannel注册到selector中,同时监听读事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 5.循环等待连接客户端
        while (true){
            if(selector.select(1000)==0){
                System.out.println("服务器等待1秒，无连接");
                continue;
            }
            // 5.1 如果select()>0表示有事件,遍历所有事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()){
                SelectionKey selectionKey = it.next();
                if(selectionKey.isAcceptable()){
                    // 5.2 判断事件为连接事件,则给客户端生成一个连接是通道,设置为非阻塞模式
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 5.3 将客户端通道注册到selector中，同时关注读数据事件
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){
                    // 5.4为读事件，则获取客户端连接通道
                    SocketChannel SocketChannel =(SocketChannel) selectionKey.channel();
                    // 5.5 从客户端连接通道中获取ByteBuffer对象，并读取来自客户端通道的数据到buffer对象中
                    ByteBuffer byteBuffer =(ByteBuffer) selectionKey.attachment();
                    SocketChannel.read(byteBuffer);
                    System.out.println("读取客户单数据："+new String(byteBuffer.array()));
                    // 5.6清除缓冲区
                    byteBuffer.clear();
                }
                // 6.移除遍历过的事件，避免重复查找
                it.remove();
            }


        }

    }
}
