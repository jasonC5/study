package com.jason.study.algorithmQuestions.class49;

/**
 * 给定整数 n 和 k，找到 1 到 n 中字典序第 k 小的数字。
 * <p>
 * 注意：1 ≤ k ≤ n ≤ 109。
 * <p>
 * 示例 :
 * <p>
 * 输入:
 * n: 13   k: 2
 * <p>
 * 输出:
 * 10
 * <p>
 * 解释:
 * 字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-th-smallest-in-lexicographical-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC440 {
    //位数、可当成mask
    public static int[] offset = {0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    //有几位的时候，以特定数字开头，>= 改位数整个包含多少数字：
    public static int[] count = {0, 1, 11, 111, 1111, 11111, 111111, 1111111, 11111111, 111111111, 1111111111};

    public int findKthNumber(int n, int k) {
        // 有几位
        int len = getLength(n);
        // 开头数字
        int first = n / offset[len];
        // 按字典序分成三个部分， 开头数数<first（1~length位）  开头数字=first（1~length位） 开头数字>first（1~length-1位）
        int left = (first - 1) * count[len];
        //如果落在左边
        if (left >= k) {
            // 以几开头
            first = (k + count[len] - 1) / count[len];//向上取整
            // 第多少个
            k -= (first - 1) * count[len];
            return kth((first + 1) * offset[len] - 1/*上限*/, len, k);
        }
        //如果落在中间
        int mid = n % (offset[len]) + 1/*最高位和n相同，位数也相同，后面：0~n的后面*/ + count[len - 1]/*位数不同，直接拿*/;
        if (mid >= k - left) {
            return kth(n, len, k - left);
        }
        // 那就只有落在右边，说明最高位比n的最高位还大，那位数就得减一位了
        len -= 1;
        k -= left + mid;//把之前的减掉
        int add = (k + count[len] - 1) / count[len];//向上取整  多出来的位数
        return kth((first + add + 1) * offset[len] - 1/*上限*/, len, k - (add - 1) * count[len]);
    }

    private int kth(int max, int len, int k) {
        // 中间范围还管不管的着！
        // 有任何一步，中间位置没命中，左或者右命中了，那以后就都管不着了！
        // 但是开始时，肯定是管的着的！
        boolean closeToMax = true;
        //第一位
        int ans = max / offset[len];
        while (--k > 0) {//一位一位的算出来
            max %= offset[len--];//去掉最高位置
            if (!closeToMax) {
                int num = (k - 1) / count[len];
                ans = ans * 10 + num;
                k -= num * count[len];
            } else {
                int first = max / offset[len];
                int left = first * count[len];
                //和上面的方法类似，左
                if (left >= k) {
                    closeToMax = false;
                    int num = (k - 1/*为什么要减1，因为此时可以是0了*/) / count[len];
                    ans = ans * 10 + num;
                    k -= num * count[len];
                    continue;
                }
                // 中
                k -= left;
                int mid = count[len - 1] + (max % offset[len]) + 1;
                if (mid >= k - left) {
                    ans = ans * 10 + first;
                    continue;
                }
                // 右
                k -= mid;
                closeToMax = false;
                len -= 1;
                int add = (k + count[len] - 1) / count[len];//向上取整  多出来的位数
                ans = ans * 10 + (first + add);
                k -= (add - 1) * count[len];
            }
        }
        return ans;
    }

    private int getLength(int n) {
        int length = 0;
        while (n != 0) {
            n /= 10;
            length++;
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(new LC440().findKthNumber(9885387, 8786251));
    }
}
