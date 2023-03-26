package com.bange.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Channel实现文件拷贝
 */
public class ChannelCopyeFileByTransferFrom {
    public static void main(String[] args) throws  Exception{
        // 1.创建流
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/admin/Desktop/file-01.jpg"));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/admin/Desktop/io-demo/file-c01-copy.jpg"));
        // 2.创建通道
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();
        // 3.执行从资源通道写入目标通道
        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        // 4.关闭资源
        sourceChannel.close();
        destChannel.close();
        fileInputStream.close();
        fileOutputStream.close();


    }
}
