package com.bange.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {
    private static  final  String HOST="127.0.0.1";
    private static  final  int PORT=9980;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() {
        try {
            selector=Selector.open();
            socketChannel=SocketChannel.open(new InetSocketAddress(HOST,PORT));

            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName=socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(userName+"is ok ^^ ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(String msg){
        try {
            System.out.println(userName+"说："+msg);
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void readServerMessage(){
        try {
            int select = selector.select(2000);
            if(select>0){
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                while (it.hasNext()){
                    SelectionKey key = it.next();
                    if(key.isReadable()){
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        String msg= new String(byteBuffer.array());
                        System.out.println(channel.getRemoteAddress()+" : "+msg.trim());
                    }
                    it.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    groupChatClient.readServerMessage();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.nextLine();
            groupChatClient.sendMessage(msg);
        }

    }
}
