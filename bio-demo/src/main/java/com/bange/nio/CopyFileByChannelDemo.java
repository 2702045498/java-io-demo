package com.bange.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFileByChannelDemo {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(new File("/Users/admin/Desktop/1676880844931.txt"));
            FileOutputStream fos = new FileOutputStream(new File("/Users/admin/Desktop/" + System.currentTimeMillis() + ".txt"));
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true){
                buffer.clear();
                int read = fisChannel.read(buffer);
                if(read==-1){
                    break;
                }
                buffer.flip();
                fosChannel.write(buffer);
            }
            fisChannel.close();
            fosChannel.close();

        }catch (Exception e){
e.printStackTrace();
        }

    }
}
