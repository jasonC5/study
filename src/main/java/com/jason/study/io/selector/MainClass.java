package com.jason.study.io.selector;

public class MainClass {
    /**
     * 多路复用器
     * 一个多路复用器线程处理 accept
     * 多个多路复用器线程处理 recv
     *
     */
    public static void main(String[] args) {
        //创建一个接收客户端的多路复用器组      accept
        SelectorGroup boosGroup = new SelectorGroup(2);
        //创建一个处理客户端请求的多路复用器组       recv
        SelectorGroup workerGroup = new SelectorGroup(4);
        //将处理请求的多路复用器组，关联到接收请求的多路复用器上（接收后扔给别的线程处理）
        boosGroup.setWorker(workerGroup);
        //绑定端口
        boosGroup.bind(8181);
        boosGroup.bind(8182);
        boosGroup.bind(8183);
        boosGroup.bind(8184);
    }



}
