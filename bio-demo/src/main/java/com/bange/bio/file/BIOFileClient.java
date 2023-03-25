package com.bange.bio.file;

import java.io.*;
import java.net.Socket;

//单发消息客户端
public class BIOFileClient {
    public static void main(String[] args) throws Exception {
        //(1)创建socket请求服务端
        Socket socket = new Socket("127.0.0.1",8888);
        //(2)获取一个字符输出流
        OutputStream os = socket.getOutputStream();
        //(3)包装成数据输出流
        DataOutputStream dos = new DataOutputStream(os);
        //(4)先发送文件的后缀名
        dos.writeUTF(".png");
        InputStream is = new FileInputStream(new File("/Users/admin/Desktop/file-01.jpg"));
        byte[] buf=new byte[1024];
        int len=0;

        while ((len=is.read(buf))>0){
            dos.write(buf,0,len);
        }
        dos.flush();
        //通知服务端输出结束
        socket.shutdownOutput();
    }
}
