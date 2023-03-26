package com.bange.nio.selector;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClientDemo {
    public static void main(String[] args) throws Exception{
        // 1.获取一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 1.1设置非阻塞
        socketChannel.configureBlocking(false);
        // 2.获取连接地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        boolean connect = socketChannel.connect(inetSocketAddress);
        if(!connect){
            while (!socketChannel.finishConnect()){
                System.out.println("客户端没有连接，不会阻塞，可以继续其他事件");
            }
        }
        // 3.连接成功，则发送数据,wrap是包装byteBuffer,同时设置buffer的容量为str的长度
        String str="hello world bange";
        socketChannel.write(ByteBuffer.wrap(str.getBytes()));

    }
}
