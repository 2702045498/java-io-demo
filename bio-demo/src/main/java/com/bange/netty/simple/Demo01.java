package com.bange.netty.simple;

import io.netty.util.NettyRuntime;

public class Demo01 {
    public static void main(String[] args) {
        // 系统CPU核数
        int i = NettyRuntime.availableProcessors();
        System.out.println(i);
    }
}
