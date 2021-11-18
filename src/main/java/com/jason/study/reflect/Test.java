package com.jason.study.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {



        int[] arr1 = {1, 2, 3};
        Class<? extends int[]> class1 = arr1.getClass();
        int[] arr2 = {1, 2, 3};
        Class<? extends int[]> class4 = arr2.getClass();
        int[] arr3 = {4, 5, 6};
        Class<? extends int[]> class3 = arr3.getClass();
        System.out.println(class1 == class4);
        System.out.println(class1 == class3);

        System.out.println("-------------------------------");

        String path = "com.jason.study.reflect.AliPay";
        Class clazz = Class.forName(path);
        //构造器
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        Arrays.stream(declaredConstructors).forEach(System.out::println);

        Constructor constructor1 = clazz.getConstructor(int.class);
        System.out.println(constructor1);

        Constructor constructor = clazz.getConstructor();
        Object obj = constructor.newInstance();
        //方法
        Method payOnline = clazz.getMethod("payOnline", Double.class);
        Object invoke = payOnline.invoke(obj, 10D);
        System.out.println("result:" + invoke);
        //属性
        Field a = clazz.getDeclaredField("a");
        Object o = a.get(obj);
        System.out.println("Field:" + o);
        System.out.println("Field class:" + o.getClass().getName());
        Annotation[] annotations = clazz.getAnnotations();
        System.out.println(annotations);
        int modifiers = a.getModifiers();
        System.out.println("modifiers::" + modifiers);
        System.out.println("modifiers::" + Modifier.toString(modifiers));
        a.set(obj, 100);
        System.out.println(obj);
        //类加载器
        WechatPay wechatPay = new WechatPay();
        ClassLoader classLoader = Test.class.getClassLoader();
        Class<?> class2 = classLoader.loadClass("com.jason.study.reflect.WechatPay");
        Method class2Method = class2.getMethod("payOnline", Double.class);
        Object class2Invoke = class2Method.invoke(wechatPay, 10D);
        System.out.println("result2:" + class2Invoke);
        Class<?>[] interfaces = class2.getInterfaces();
        System.out.println("interfaces:" + interfaces);

        Method[] methods = class2.getMethods();
        Arrays.stream(methods).forEach(System.out::println);
        Method[] declaredMethods = class2.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(System.out::println);

        Method test = class2.getDeclaredMethod("test", int.class);
        Class<?> returnType = test.getReturnType();
        Class<?>[] parameterTypes = test.getParameterTypes();

        System.out.println(test);
        System.out.println("----------------------------------------");
        Package aPackage = class2.getPackage();
        System.out.println(aPackage.getName());
        Class<?> superclass = class2.getSuperclass();
        System.out.println(superclass);
    }
}
