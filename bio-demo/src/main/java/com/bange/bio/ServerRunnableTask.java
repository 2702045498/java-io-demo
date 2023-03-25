package com.bange.bio;

import java.io.*;
import java.net.Socket;

public class ServerRunnableTask  implements Runnable{
    private Socket socket;
    public ServerRunnableTask(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {
        try{
            //(1)从socket管道中获取一个输入流对象
            InputStream is = socket.getInputStream();
            //(2)把字节包装成一个缓冲字符流
            DataInputStream dis = new DataInputStream(is);
            String suffixName = dis.readUTF();
            System.out.println("服务端开始接收文件"+suffixName);
            //(3)字节输出
            OutputStream fos = new FileOutputStream(new File("/Users/admin/Desktop/io-demo/"+System.currentTimeMillis() + suffixName));
            byte[] buf=new byte[1024];
            int len=0;
            while ((len=dis.read(buf))>0){
                fos.write(buf,0,len);
            }
            fos.close();
            System.out.println("服务端接收完成");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
