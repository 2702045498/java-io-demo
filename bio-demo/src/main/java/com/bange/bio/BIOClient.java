package com.bange.bio;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
//单发消息客户端
public class BIOClient {
    public static void main(String[] args) throws Exception {
        //(1)创建socket请求服务端
        Socket socket = new Socket("127.0.0.1",9999);
        //(2)获取一个字符输出流
        OutputStream os = socket.getOutputStream();
        //(3)包装成打印流
        PrintStream printStream = new PrintStream(os);
        printStream.println("hello");
        printStream.flush();
    }
}
