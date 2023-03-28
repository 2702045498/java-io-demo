package com.bange.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) throws  Exception{
        Socket socket = new Socket("127.0.0.1",9998);
        String fileName="/Users/admin/Desktop/io-demo/nacos-server-2.2.0.1.tar.gz";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buf=new byte[1024];
        long readCount=0;
        long total=0;

        long start = System.currentTimeMillis();
        while ((readCount=fileInputStream.read(buf))>0){
            total++;
            dataOutputStream.write(buf);
        }
        System.out.println("发送总字数："+total+"耗时："+(System.currentTimeMillis()-start));
        dataOutputStream.close();
        socket.close();
        fileInputStream.close();

    }
}
