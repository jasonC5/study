package com.jason.study.proxy.cglib;

import com.jason.study.proxy.jdk.Calc;

public class MyCalc {
    public int addition(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public int mode(int a, int b) {
        return a % b;
    }
}
