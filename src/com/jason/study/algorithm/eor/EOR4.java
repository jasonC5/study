package com.jason.study.algorithm.eor;

/**
 * 一个数组中有两种数出现了技术粗，其他数都出现了偶数字
 * @author JasonC5
 */
public class EOR4 {

    public static void main(String[] args) {
        int[] arr = {1,2,2,3,3,3,4,4,5,5,5,5,};
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //eor = a ^ b
        int mask = eor & (-eor);//随便找到eor中一位是1的，这里找的最右边的，这种方式快
        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            //把数组分城两部分，一部分是该位置为1的，一部分是该位置为0的，随便取一半异或起来，就能得到其中一个数
            if ((arr[i] & mask) ==0){a ^= arr[i];}
        }
        int b = eor ^ a;// a^b^a = b
        System.out.println(a+","+b);
    }
}
