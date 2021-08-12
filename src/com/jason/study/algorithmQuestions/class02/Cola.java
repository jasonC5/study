package com.jason.study.algorithmQuestions.class02;

/**
 * 贩卖机只支持硬币支付，且收退都只支持10 ，50，100三种面额
 * 一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则
 * 需要购买的可乐数量是m，
 * 其中手头拥有的10、50、100的数量分别为a、b、c
 * 可乐的价格是x(x是10的倍数)
 * 请计算出需要投入硬币次数？
 * --面值入手
 * --向上取整技巧：(a+(x-1)）/ x
 * --前面剩余的钱和剩余的张数和自己搞定第一瓶
 * --自己面额多少张能搞定一瓶，找零多少，加给后面
 * --当前面额搞定了多少瓶，用了多少张，剩了多少张、多少钱
 *
 * @author JasonC5
 */
public class Cola {


    // 正式的方法
    // 要买的可乐数量，m
    // 100元有a张
    // 50元有b张
    // 10元有c张
    // 可乐单价x
    public static int putTimes(int m, int a, int b, int c, int x) {
        int[] qian = {100, 50, 10};
        int[] zhang = {c, b, a};
        //要买m瓶可乐，需要投币的次数
        int count = 0;
        //视角定位在面额上
        int preMoney = 0;//上一个面额处理完剩下的金额
        int preCount = 0;//上一个面额处理完剩下的张数【两个字段代替两个数组】
        for (int i = 0; i < zhang.length && m != 0; i++) {
            //先解决第一瓶(向上取整 (a+(x-1)）/ x )
            int firstCount = (x - preMoney + qian[i] - 1) / qian[i];
            if (zhang[i] >= firstCount) {
                //能搞定第一瓶
                //减去相应张数
                zhang[i] -= firstCount;
                //找零
                change(qian, zhang, i + 1, preMoney + firstCount * qian[i] - x, 1);
                //总投币数          （加上前面剩下的）
                count += firstCount + preCount;
                //搞定一瓶
                m--;
//                preMoney = 0;
//                preCount = 0;
            } else {
                //搞不定第一瓶，留给后面
                preMoney += zhang[i] * qian[i];
                preCount += zhang[i];
                continue;
            }
            //第一瓶搞定了，看剩下的钱，能搞定几瓶
            int oneCount = (x + qian[i] - 1) / qian[i];
            int colas = Math.min(m, (zhang[i] / oneCount));
            //总投币数
            count += oneCount * colas;
            //找零
            change(qian, zhang, i + 1, oneCount * qian[i] - x, colas);
            //剩下几张,剩多少钱
            zhang[i] -= oneCount * colas;
            preCount = zhang[i];
            preMoney = preCount * qian[i];
            //还要买多少瓶
            m -= colas;
        }
        return m == 0 ? count : -1;
    }

    private static void change(int[] qian, int[] zhang, int index, int rest, int times) {
        for (; index < 3; index++) {
            zhang[index] += (rest / qian[index]) * times;
            rest %= qian[index];
        }
    }

    // 暴力尝试，为了验证正式方法而已
    public static int right(int m, int a, int b, int c, int x) {
        int[] qian = {100, 50, 10};
        int[] zhang = {c, b, a};
        int puts = 0;
        while (m != 0) {
            int cur = buy(qian, zhang, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    public static int buy(int[] qian, int[] zhang, int rest) {
        int first = -1;
        for (int i = 0; i < 3; i++) {
            if (zhang[i] != 0) {
                first = i;
                break;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (qian[first] >= rest) {
            zhang[first]--;
            change(qian, zhang, first + 1, qian[first] - rest, 1);
            return 1;
        } else {
            zhang[first]--;
            int next = buy(qian, zhang, rest - qian[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;
        }
    }

    public static void main(String[] args) {
        int testTime = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * colaMax);
            int a = (int) (Math.random() * zhangMax);
            int b = (int) (Math.random() * zhangMax);
            int c = (int) (Math.random() * zhangMax);
            int x = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = putTimes(m, a, b, c, x);
            int ans2 = right(m, a, b, c, x);
            if (ans1 != ans2) {
                System.out.println("int m = " + m + ";");
                System.out.println("int a = " + a + ";");
                System.out.println("int b = " + b + ";");
                System.out.println("int c = " + c + ";");
                System.out.println("int x = " + x + ";");
                break;
            }
        }
        System.out.println("test end");
    }

//    public static void main(String[] args) {
//        int ans1 = putTimes(8, 5, 1, 6, 60);
//        int ans2 = right(8, 5, 1, 6, 60);
//        if (ans1 != ans2) {
//            System.out.println("failed");
//        }
//    }

}
