package com.bange.nio;


import java.nio.ByteBuffer;

public class BufferApiDemo {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(10);
        System.out.println("==>position():"+allocate.position());
        System.out.println("limit():"+allocate.limit());
        System.out.println("capacity():"+allocate.capacity());
        System.out.println("-----------------");
        //put向缓冲区添加数据
        allocate.put("abc".getBytes());
        System.out.println("==>position():"+allocate.position());
        System.out.println("limit():"+allocate.limit());
        System.out.println("capacity():"+allocate.capacity());
        System.out.println("-----------------");
        //将缓冲区的界限设置为当前位置，并将当前位置设置为0，可读模式
        allocate.flip();
        System.out.println("==>position():"+allocate.position());
        System.out.println("limit():"+allocate.limit());
        System.out.println("capacity():"+allocate.capacity());
        //get读取数据
        System.out.println("-----------------");
        char b =(char)allocate.get();
        System.out.println(b);
        System.out.println("-----------------");
        //clear（）清除缓冲区
        allocate.clear();
        System.out.println("==>position():"+allocate.position());
        System.out.println("limit():"+allocate.limit());
        System.out.println("capacity():"+allocate.capacity());

    }
}
