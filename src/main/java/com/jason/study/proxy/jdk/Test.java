package com.jason.study.proxy.jdk;

public class Test {

    public static void main(String[] args) {
//        Calc proxy = TProxy.getCalcProxy(new MyCalc());
//        int addition = proxy.addition(1, 2);
//        System.out.println(proxy);
        Calc proxy = TProxy.getProxy(new MyCalc());
        int addition = proxy.addition(1, 2);
        System.out.println(proxy);
    }
}
