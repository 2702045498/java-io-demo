package com.bange.netty.dubborpc.provider;

import com.bange.netty.dubborpc.rpcserver.RPCNettyServer;

/**
 * 启动类
 */
public class RPCServerBootstrap {
    public static void main(String[] args) {
        RPCNettyServer.startRpcServer("127.0.0.1",9992);
    }
}
