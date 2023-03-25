package com.bange.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream(new File("/Users/admin/Desktop/" + System.currentTimeMillis() + ".txt"));
            FileChannel channel = fos.getChannel();
            String msg="hello world channel";
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(msg.getBytes());
            //把缓冲区改成写出模式
            buffer.flip();
            channel.write(buffer);
            channel.close();
            System.out.println("写数据到文件中……");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
