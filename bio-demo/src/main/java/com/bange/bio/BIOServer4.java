package com.bange.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//伪异步I/O--线程池+消息队列
public class BIOServer4 {
    public static void main(String[] args) throws IOException {
        //(1)定义serversocket端口注册
        ServerSocket serverSocket = new ServerSocket(9999);
        //初始化线程池对象
        HandlerSocketThreadPool socketThreadPool = new HandlerSocketThreadPool(10, 10);
        while (true){
            //(2)监听客户端的连接请求
            Socket socket = serverSocket.accept();
            //把socket封装成一个任务对象
            Runnable runnableTask = new ServerRunnableTask(socket);
            //提交任务给线程池
            socketThreadPool.excute(runnableTask);
        }


    }
}
