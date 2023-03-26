package com.bange.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCopyFile {
    public static void main(String[] args)throws Exception {
        // 创建输入/输出流
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/admin/Desktop/io-demo/filechannel.txt"));
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/admin/Desktop/io-demo/filechannelcopy.txt");

        // 获取通道
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer =  ByteBuffer.allocate(1024);

        while (true){

            // 循环重置缓冲区: 每次读写完成后，需要清空前一次读的内容
            byteBuffer.clear();
            int read = fileInputStreamChannel.read(byteBuffer);
            if(read==-1){
                break;
            }
            //切换bytebuffer读写
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);

        }

        // 释放资源
        fileOutputStream.close();
        fileInputStreamChannel.close();
    }
}
