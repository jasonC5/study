package com.jason.study.io.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorGroup {//分组

    SelectorThread[] selectorThreadArr;
    SelectorGroup worker =  this;
    AtomicInteger sid = new AtomicInteger(0);//id，哈希取模取selector
    AtomicInteger cid = new AtomicInteger(0);//id，哈希取模取selector
    ServerSocketChannel server=null;

    public void setWorker(SelectorGroup  worker){//worker组
        this.worker = worker;
    }

    public SelectorGroup(int threadNum) {//构造器
        selectorThreadArr = new SelectorThread[threadNum];//多路复用器组
        for (int i = 0; i< threadNum; i++) {
            selectorThreadArr[i] = new SelectorThread(this);//先把自己塞给多路复用器
            new Thread(selectorThreadArr[i]).start();
        }
    }

    public void bind(int port){
        if (port <0 || port > 65535) {
            System.out.println("port value error");
        }
        try {
            server = ServerSocketChannel.open();//得到一个fd
            server.configureBlocking(false);//非阻塞
            server.bind(new InetSocketAddress(port));
            //注册到一个多路复用器上（boos多路复用器）
            nextBoosSelectorRegister(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void nextBoosSelectorRegister(ServerSocketChannel server) {//bind用的
        SelectorThread selectorThread = nextBoosSelector();//得到boos的多路复用器
        try {
            selectorThread.fdList.put(server);//往容器中加
//            selectorThread.setWorker(worker); 是否需要这一步？？？
            selectorThread.selector.wakeup();//叫醒，否则会一直阻塞在 selector.select() 这一步
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SelectorThread nextBoosSelector(){
        int id = sid.incrementAndGet() % selectorThreadArr.length;
        System.out.println("boos Thread id:"+id);
        return selectorThreadArr[id];
    }

    public void nextWorkerSelectorRegister(SocketChannel socket) {//accept用的
        SelectorThread selectorThread = nextWorkerSelector();//得到worker的多路复用器
        try {
            selectorThread.fdList.put(socket);
            selectorThread.selector.wakeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SelectorThread nextWorkerSelector(){
        int id =  cid.incrementAndGet() % worker.selectorThreadArr.length;
        System.out.println("worker Thread id:"+id);
        return worker.selectorThreadArr[id];
    }


}
