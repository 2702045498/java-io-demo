package com.bange.nio;

import java.nio.IntBuffer;

public class BufferApiDemo1 {
    public static void main(String[] args) {
        //  1. buffer大小为5 ，可以存储5个int类型的数据
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
        intBuffer.put(4);
        intBuffer.put(5);
//        intBuffer.put(6);
        // 2.从buffer中读取数据,需要进行读写切换
        intBuffer.flip();
        // 3.
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
