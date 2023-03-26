package com.bange.nio.buffer;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScattingAndGettingBufferDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定地址
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",9999));
        // 启动
        serverSocketChannel.socket();
        ByteBuffer[] byteBufferArr=new ByteBuffer[2];
        byteBufferArr[0]=ByteBuffer.allocate(5);
        byteBufferArr[1]=ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;// 假设每次读取8个字节
        while (true){
            int byteRead=0;
            while (byteRead<messageLength){
                long len = socketChannel.read(byteBufferArr);
                byteRead+=len;
//                System.out.println("byteRead="+byteRead);
//                Arrays.asList(byteBufferArr).stream().map(byteBuffer -> "position="+byteBuffer.position()
//                        +"limit="+byteBuffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBufferArr).stream().forEach(byteBuffer -> byteBuffer.flip());
            // 回显到客户端
            int byteWrite=0;
            while (byteWrite<messageLength){
                long len = socketChannel.write(byteBufferArr);
                byteWrite+=len;
//                System.out.println("byteWrite="+byteWrite);

            }
            Arrays.asList(byteBufferArr).stream().forEach(byteBuffer-> System.out.println(new String(byteBuffer.array())));
            // 将所有buffer清理
            Arrays.asList(byteBufferArr).stream().forEach(byteBuffer->byteBuffer.clear());


//            System.out.println("byteread="+byteRead+"byteWrite="+byteWrite+"messageLength="+messageLength);

        }

    }
}
