package com.bange.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以让文件在内存（堆外内存）中直接修改，而不需要拷贝文件
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/admin/Desktop/io-demo/filechannel.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /*
            FileChannel.MapMode.READ_WRITE:读写模式
            0：是文件可以修改的起始位置
            5：是映射到内存的大小（不是索引），即是将文件的多少个字节映射到内存
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
//        mappedByteBuffer.put(2,"W".getBytes()); // JDK13
//        mappedByteBuffer.put(4,"H".getBytes());
        randomAccessFile.close();
    }
}
