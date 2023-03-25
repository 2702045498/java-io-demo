package com.bange.bio;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

//多发多收机制
public class BIOClient2 {
    public static void main(String[] args) throws Exception {
        //(1)创建socket请求服务端
        Socket socket = new Socket("127.0.0.1",9999);
        //(2)获取一个字符输出流
        OutputStream os = socket.getOutputStream();
        //(3)包装成打印流
        PrintStream printStream = new PrintStream(os);
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.print("客户端1请输入：");
            String msg = sc.nextLine();
            printStream.println(msg);
            printStream.flush();
        }

    }
}
