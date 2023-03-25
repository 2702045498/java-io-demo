package com.bange.bio.file;

import com.bange.bio.HandlerSocketThreadPool;
import com.bange.bio.ServerRunnableTask;

import java.net.ServerSocket;
import java.net.Socket;

//接收任意类型文件
public class BIOFileServer {
    public static void main(String[] args) throws Exception{
        //(1)定义serversocket端口注册
        ServerSocket serverSocket = new ServerSocket(8888);
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
