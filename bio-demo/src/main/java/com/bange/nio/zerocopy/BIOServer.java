package com.bange.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统IO模型
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9998);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] buf = new byte[1204];
            while (true){
                int read = dataInputStream.read(buf, 0, buf.length);
                if(-1==read){
                    break;
                }

            }
        }
    }
}
