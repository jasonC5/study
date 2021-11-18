package com.jason.study.algorithm.other;

/**
 * 蓄水池算法--等概率
 * <p>
 * 假设有一个源源吐出不同球的机器，
 * 只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
 * 如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
 *
 * @author JasonC5
 */
public class Reservoir_01 {

    //处理的所有数据，等概率留在bag中
    public static class RandomBox {
        //池子的容量
        private int len;
        //池子
        private int[] bag;
        //小球的数量
        int count;

        public RandomBox(int len) {
            this.len = len;
            this.bag = new int[len];
            this.count = 0;
        }

        public void add(int boll) {
            if (count++ < len) {
                //没满，直接放
                bag[count - 1] = boll;
            } else {
                //满了 len / n 的概率放进去
                //若能进去，等概率换掉bag中的任何一个 1 / len
                if (getRandom(count) <= len) {
                    bag[getRandom(len) - 1] = boll;
                }
            }
        }

        private int getRandom(int count) {
            //等概率返回从1-count
            return (int) (Math.random() * count) + 1;
        }

        public int[] get() {
            int[] ans = new int[len];
            for (int i = 0; i < len; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }
    }

}
