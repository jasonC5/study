package com.jason.study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglib implements MethodInterceptor {

    /**
     * @param obj    要增强的对象， 即实现这个接口类的一个对象
     * @param method 表示要拦截的方法
     * @param args   表示被拦截的方法的参数
     * @param proxy  表示要触发父类的方法对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before " + method.getName());
        Object res = proxy.invokeSuper(obj, args);
        System.out.println("after " + method.getName());
        return res;
    }
}
