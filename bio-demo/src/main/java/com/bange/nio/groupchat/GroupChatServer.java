package com.bange.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9980;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) {//有事件处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        // 有连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        // 读事件
                        if (key.isReadable()) {
                            readClientData(key);
                        }
                    }
                    // 删除key事件
                    it.remove();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


    public void readClientData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len = socketChannel.read(byteBuffer);
            if(len>0){
                String msg=new String(byteBuffer.array());
                System.out.println("from 客户端："+msg.trim());
                // 向其他客户端转发消息
                sendToOtherClient(msg,socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }


    public void sendToOtherClient(String msg,SocketChannel socketChannel){
        try {
            for (SelectionKey key:selector.keys()) {
                Channel channel = key.channel();
                System.out.println(channel != socketChannel);
                // 排除客户端本身
                if(channel instanceof SocketChannel && channel!=socketChannel){
                    SocketChannel sc=(SocketChannel) channel;
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                    sc.write(byteBuffer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
