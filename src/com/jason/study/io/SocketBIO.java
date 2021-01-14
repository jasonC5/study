package com.jason.study.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBIO {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090,10);//
        while (true){
            Socket client = serverSocket.accept();//有一个请求打过来，打过来之前，阻塞的
            System.out.println("accept one client :"+client.getInetAddress()+"_"+client.getPort());
            //new 一个线程处理请求
            new Thread(()->{
                InputStream inputStream = null;
                try {
                    inputStream = client.getInputStream();//传过来的消息
//                    new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//读取
                    OutputStream out = System.out;
                    while (true){
                        String readLine = reader.readLine();//阻塞的
                        System.out.println("got a message");
                        if (readLine==null){
                            client.close();
                            break;
                        } else {
                            if (readLine.equals("exit")) {
                                inputStream.close();
                            } else {
                                //                            out.write(readLine.getBytes());
                                System.out.println(readLine);
                                OutputStream outputStream = client.getOutputStream();
                                outputStream.write("got it!\n".getBytes());
                            }
                        }
//                        out.write("客户端断开连接\n".getBytes());
                    }
                    out.write("client off line\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },"Thread_"+client.getInetAddress()+"_"+client.getPort()).start();
        }
    }
}
