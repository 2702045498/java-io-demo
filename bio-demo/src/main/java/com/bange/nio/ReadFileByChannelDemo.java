package com.bange.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFileByChannelDemo {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(new File("/Users/admin/Desktop/1676880844931.txt"));
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //读取文件数据
            channel.read(buffer);
            buffer.flip();
            String msg = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(msg);
            channel.close();
            System.out.println("读取文件数据到完成");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
