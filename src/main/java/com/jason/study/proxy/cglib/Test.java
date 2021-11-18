package com.jason.study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Test {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyCalc.class);
        enhancer.setCallback(new MyCglib());

        MyCalc myCalc = (MyCalc) enhancer.create();
        int addition = myCalc.addition(1, 2);
        System.out.println(myCalc);

    }
}
