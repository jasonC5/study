package com.jason.study.thread;

public class CAS_Test {

    enum FLAG {X,Y}

    static volatile FLAG flag = FLAG.X;

    public static void main(String[] args) {
        char[] a = "ABCDEFGHI".toCharArray();
        char[] b = "123456789".toCharArray();

        new Thread(()->{
//            int i = 0;
//            while (flag == FLAG.X){
//                if (i < a.length){
//                    System.out.print(a[i++]);
//                    System.out.println(i);
//                    flag = FLAG.Y;
//                } else {
//                    break;
//                }
//            }
            for (char c : a) {
                while (flag !=FLAG.X){}
                System.out.print(c);
                flag = FLAG.Y;
            }
        }).start();

        new Thread(()->{
//            int i = 0;
//            while (flag == FLAG.Y){//不能这么写的原因，里面false了就不
//                if (i < b.length){
//                    System.out.print(b[i++]);
//                    System.out.println(i);
//                    flag = FLAG.X;
//                } else {
//                    break;
//                }
//            }
            for (char c : b) {
                while (flag !=FLAG.Y){}
                System.out.print(c);
                flag = FLAG.X;
            }
        }).start();
    }
}
