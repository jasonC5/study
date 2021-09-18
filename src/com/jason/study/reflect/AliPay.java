package com.jason.study.reflect;

import org.junit.Ignore;

@Ignore
public class AliPay implements Pay {

    public /*static final*/ int a = 2;

    @Override
    public Boolean payOnline(Double amount) {
        System.out.println("Ali Pay Online:" + amount);
        return true;
    }

    public AliPay() {

    }

    public AliPay(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "AliPay{" +
                "a=" + a +
                '}';
    }
}
