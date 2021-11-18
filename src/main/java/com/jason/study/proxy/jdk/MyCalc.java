package com.jason.study.proxy.jdk;

public class MyCalc implements Calc {
    @Override
    public int addition(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    @Override
    public int divide(int a, int b) {
        return a / b;
    }

    @Override
    public int mode(int a, int b) {
        return a % b;
    }
}
