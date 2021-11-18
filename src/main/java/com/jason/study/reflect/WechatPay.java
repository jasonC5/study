package com.jason.study.reflect;

public class WechatPay implements Pay {
    @Override
    public Boolean payOnline(Double amount) {
        System.out.println("wechat pay online:" + amount);
        return true;
    }

    private Integer test(int a){
        return a;
    }
}
