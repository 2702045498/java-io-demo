package com.bange.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {
    public static void main(String[] args) {

        try {
            //1获取通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",7777));

            //2切换非阻塞模式
            socketChannel.configureBlocking(false);
            //3指定缓冲区大小
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Scanner scanner=new Scanner(System.in);
            while (true){
                System.out.print("输入：");
                String msg = scanner.nextLine();
                buffer.put(msg.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
