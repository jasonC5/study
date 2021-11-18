package com.jason.study.io.selector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SelectorThread implements Runnable{//线程
    //一个线程对应一个多路复用器selector
    //多线程情况下，
    //每个客户端，只绑定到其中一个selector
    //
    Selector selector = null;
    LinkedBlockingQueue<Channel> fdList = new LinkedBlockingQueue<>();//往多路复用器中扔的，需要关注的fd
    SelectorGroup selectorGroup = null;//所在组

    public SelectorThread(SelectorGroup selectorGroup) {
        this.selectorGroup = selectorGroup;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                //1,select()
//                System.out.println(Thread.currentThread().getName()+"   :  before select...."+ selector.keys().size());
                int nums = selector.select();//本次多路复用器中获取到的状态改变的fd  会阻塞  等待wakeup()
                if (nums > 0) {
                    //处理本selector中关注的事务
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for(SelectionKey selectionKey: keys){
                        if (selectionKey.isAcceptable()){//处理新客户端接收
                            acceptHandler(selectionKey);
                        } else if(selectionKey.isReadable()){//处理已接收客户端发起的请求
                            System.out.println("picked_deal_reading_--------");
                            readHandler(selectionKey);
                        } else if (selectionKey.isWritable()){//可写状态，不取消关注的话，会死循环
                            System.out.println("writable cancel");
                            selectionKey.cancel();
                        } else {
                            System.out.println("未能获取到状态变更类型");
                        }
                    }
                    keys.removeAll(keys);//没有清空导致会死循环？
//                    Iterator<SelectionKey> iter = keys.iterator();
//                    while(iter.hasNext()){  //线程处理的过程
//                        SelectionKey key = iter.next();
//                        iter.remove();
//                        if(key.isAcceptable()){  //复杂,接受客户端的过程（接收之后，要注册，多线程下，新的客户端，注册到那里呢？）
//                            acceptHandler(key);
//                        }else if(key.isReadable()){
//                            readHandler(key);
//                        }else if(key.isWritable()){
//                            key.cancel();
//                        }
//                    }
                } else {
                    System.out.println("selector got non nums="+nums);
                }
                //处理一些其他的事情（往多路复用器中添加需要关注的fd）
                if(!fdList.isEmpty()){
                    Channel channel = fdList.take();
                    //下面两个instance 是在不同的线程中发生的，（可称之为boos组合worker组），
                    // 所以虽然代码上看是往同一个selector中注册，但实际上是往不同的selector中注册
                    if (channel instanceof ServerSocketChannel) {//绑定端口号之后，需要关注、accept的fd
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//关注accept类型的状态
                        System.out.println(Thread.currentThread().getName()+" register listen "+serverSocketChannel.getLocalAddress());
                    } else if (channel instanceof SocketChannel){//accept到的客户端连接的fd，放到worker的多路复用器中，观测客户端请求
                        SocketChannel socketChannel = (SocketChannel) channel;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                        socketChannel.register(selector,SelectionKey.OP_READ, buffer);//关注读取状态
                        System.out.println(Thread.currentThread().getName()+" register accept client: " + socketChannel.getRemoteAddress());
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHandler(SelectionKey selectionKey) {
        System.out.println(Thread.currentThread().getName() + " deal read......");
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();//48行加进去的 ByteBuffer
        SocketChannel client = (SocketChannel)selectionKey.channel();
        byteBuffer.clear();
        //此时所在，worker组，自己处理请求
        while (true) {
            try {
                int size = client.read(byteBuffer);
                System.out.println("read buffer size:"+size);
                if (size > 0) {
                    //读取，处理
                    byteBuffer.flip();//将读到的内容翻转，直接写出
                    byte[] aaa = new byte[byteBuffer.limit()];
                    byteBuffer.get(aaa);//读到里面
                    String str = new String(aaa);
                    System.out.println("client request:"+str);
                    while(byteBuffer.hasRemaining()){
                        byteBuffer.clear();
                        byteBuffer.put("got it \n".getBytes());
                        byteBuffer.flip();
                        client.write(byteBuffer);//直接回写
                    }
                    byteBuffer.clear();

//                    byteBuffer.flip();  //将读到的内容翻转，然后直接写出
//                    while(byteBuffer.hasRemaining()){
//                        client.write(byteBuffer);
//                    }
//                    byteBuffer.clear();


                } else if (size == 0) {
                    break;
                } else {
                    //<0，客户端断开连接
                    System.out.println("client: " + client.getRemoteAddress()+"closed......");
                    selectionKey.cancel();//红黑树中移除关注
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("client: cancel......");
                selectionKey.cancel();
            }
        }
    }

    private void acceptHandler(SelectionKey selectionKey) {
        System.out.println(Thread.currentThread().getName() + " deal accept......");
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);//非阻塞
            // 扔给worker组，让worker组去关注具体请求
            //怎么扔呢
            selectorGroup.nextWorkerSelectorRegister(client);//交给boos组去找worker组
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
