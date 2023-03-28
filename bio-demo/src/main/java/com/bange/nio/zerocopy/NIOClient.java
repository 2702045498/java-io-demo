package com.bange.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9908));
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String fileName="/Users/admin/Desktop/io-demo/nacos-server-2.2.0.1.tar.gz";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
            long start=System.currentTimeMillis();
            long len=5*1024*1024;
            long readCount=0l;
            long position=0;
            long count=0;
            while (true){
                //transferTo方法不能超过8M 的数据
                readCount= fileChannel.transferTo(position, len, socketChannel);
                position=position+len;
                count=count+readCount;
                if(readCount==0){
                    break;
                }
            }
        System.out.println("发送总字数："+count+"耗时："+(System.currentTimeMillis()-start));

    }
}
