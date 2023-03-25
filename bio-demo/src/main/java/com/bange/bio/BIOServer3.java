package com.bange.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//多个线程--连接多个个客户端
public class BIOServer3 {
    public static void main(String[] args) throws IOException {
        //(1)定义serversocket端口注册
        ServerSocket serverSocket = new ServerSocket(9999);
        //(2)监听客户端的连接请求
        while (true){
            Socket socket = serverSocket.accept();
            new Thread(()->{
                try{
                //(3)从socket管道中获取一个输入流对象
                InputStream is = socket.getInputStream();
                //(4)把字节包装成一个缓冲字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //(5)读取字符
                String msg=null;
                while ((msg=reader.readLine())!=null){
                    System.out.println("服务器接收到："+msg);
                }}catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
