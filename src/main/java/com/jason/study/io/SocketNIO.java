package com.jason.study.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SocketNIO {
    //SocketChannel         设置成非阻塞
    //ServerSocketChannel   设置成非阻塞

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9091),10);//
        serverSocketChannel.configureBlocking(false);//非阻塞
        List<SocketChannel> clientList = new ArrayList<>();
        while (true) {
            //处理连接
            Thread.sleep(1000);
            SocketChannel client = serverSocketChannel.accept();
            if (client == null) {//没接收新的连接的时候返回为空
                System.out.println("accept non client");
            } else {//
                client.configureBlocking(false);//非阻塞
                System.out.println("accept one client :"+client.socket().getInetAddress()+"_"+client.socket().getPort());
                clientList.add(client);
            }

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);//堆外

            //处理请求信息
            List<SocketChannel> rmList = new ArrayList<>();
            for (SocketChannel socketChannel: clientList) {
                try {
                    int read = socketChannel.read(byteBuffer);
                    if (read > 0) {
                        byteBuffer.flip();
                        byte[] aaa = new byte[byteBuffer.limit()];
                        byteBuffer.get(aaa);//读到里面
                        String str = new String(aaa);
                        System.out.println(str);
//                    byteBuffer.put("got it \n".getBytes());
//                        socketChannel.write(byteBuffer);
                        if ("exit\n".equals(str)) {
                            socketChannel.close();
                            rmList.add(socketChannel);
                        } else {
//                            byteBuffer.compact();
                            byteBuffer.clear();
                            byteBuffer.put("got it \n".getBytes());
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
//                            write(byteBuffer);
//                            OutputStream outputStream = socketChannel.socket().getOutputStream();
//                            outputStream.write("got it !\n".getBytes());
                        }
                        byteBuffer.clear();
                    } else {
                        //没读到
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    rmList.add(socketChannel);
                }
            }
            clientList.removeAll(rmList);
        }
    }

}
