package com.bange.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOChannelWriteFile {
    public static void main(String[] args) throws  Exception{
        String str="hello-->channel";
        String fileName="/Users/admin/Desktop/io-demo/filechannel.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        // 1. 获取通道 FileChannelImpl是实现类
        FileChannel channel = fileOutputStream.getChannel();
        // 2. 创建一个缓冲区 ,并把数据放入缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        // 3. 切换buffer的读写模式
        byteBuffer.flip();

        // 4.channel写入数据
        channel.write(byteBuffer);
        channel.close();
        fileOutputStream.close();
    }
}
