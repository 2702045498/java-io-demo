package com.bange.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

//NIO服务端
public class NIOServer {
    public static void main(String[] args) {
        try {
            //1、获取通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //2、切换为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //3、绑定连接端口
            serverSocketChannel.bind(new InetSocketAddress(7777));
            //4、获取选择器
            Selector selector = Selector.open();
            //5、将通道注册到选择器上，并且指定监听接收时间
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //6、轮询通道是否有就绪的事件
            while (selector.select()>0){
                //7获取选择中就绪的事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //8遍历就绪事件
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //9判断事件为什么事件？
                    if(selectionKey.isAcceptable()){
                        //10直接获取当前接入的客户端通道
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //11切换成非阻塞模式
                        socketChannel.configureBlocking(false);
                        //12将客户端通道注册到选择器
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){//可能是连接后的读事件
                        //14获取当前选择上的就绪事件
                        SocketChannel channel =(SocketChannel) selectionKey.channel();
                        //15读取事件
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len=0;
                        while ((len=channel.read(buffer))>0){
                            buffer.flip();
                            System.out.println("服务端："+new String(buffer.array(),0,len));
                            buffer.clear();//清楚缓冲区
                        }
                    }
                    //13当前事件处理完成后需要移除
                    iterator.remove();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
