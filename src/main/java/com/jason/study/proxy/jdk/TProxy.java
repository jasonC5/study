package com.jason.study.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TProxy {

    public static <T> T getProxy(final T target) {
        ClassLoader classLoader = target.getClass().getClassLoader();

        Class<?>[] interfaces = target.getClass().getInterfaces();

        InvocationHandler ih = (proxy, method, args) -> {
            Object result = null;
            System.out.println("pre method call method=" + method + ",args=" + Arrays.toString(args));
            try {
                result = method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("after method call method=" + method + ",result=" + result);
            return result;
        };

        Object o = Proxy.newProxyInstance(classLoader, interfaces, ih);
        return (T) o;
    }


    public static Calc getCalcProxy(final Calc target) {
        ClassLoader classLoader = target.getClass().getClassLoader();

        Class<?>[] interfaces = target.getClass().getInterfaces();

        InvocationHandler ih = (proxy, method, args) -> {
            Object result = null;
//            System.out.println("pre method call method=" + method + ",proxy=" + proxy + ",args=" + Arrays.toString(args));
            System.out.println("before " + method.getName());
            try {
                result = method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.println("after method call method=" + method + ",result=" + result);
            System.out.println("after " + method.getName());
            return result;
        };

        Object o = Proxy.newProxyInstance(classLoader, interfaces, ih);
        return (Calc) o;
    }
}
