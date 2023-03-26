package com.bange.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioChannelReadFile {
    public static void main(String[] args) throws  Exception{
        File file = new File("/Users/admin/Desktop/io-demo/filechannel.txt");
        // 1.文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 2.获取通道
        FileChannel channel = fileInputStream.getChannel();
        // 3.创建缓冲区
        int fileLength = (int)file.length();
        ByteBuffer byteBuffer=ByteBuffer.allocate(fileLength);

        // 4.读取文件数据到channel
        channel.read(byteBuffer);

        // 5.输出读取的数据
        System.out.println(new String(byteBuffer.array()));

    }
}
