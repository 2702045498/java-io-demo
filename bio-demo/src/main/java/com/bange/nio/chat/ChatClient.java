package com.bange.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class ChatClient {
    //定义客户端的属性
    private Selector selector;
    private SocketChannel socketChannel;
    private static final int PORT = 8899;

    public ChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("==>客户端准备完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        //使用一个线程单独负责读取服务端数据
        new Thread(()->{
            chatClient.readInfo();
        }).start();

        //发送给服务端消息
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("输入消息：");
            String msg = scanner.nextLine();
            chatClient.sendToServer(msg);
        }
    }
    //发送给服务端
    private void sendToServer(String msg) {
        try {
            socketChannel.write(ByteBuffer.wrap( msg.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void readInfo() {
        try {
            while (selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        SocketChannel channel =(SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println("客户接收服务端转发："+new String(buffer.array()).trim());
                    }
                    iterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
