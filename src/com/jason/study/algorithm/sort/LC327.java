package com.jason.study.algorithm.sort;


/**
 * https://leetcode-cn.com/problems/count-of-range-sum/
 * 
 * 给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 *
 * 
 *
 * 示例 1：
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 * 示例 2：
 *
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 * 
 *
 * 提示：
 *
 * 1 <= nums.length <= 105
 * -231 <= nums[i] <= 231 - 1
 * -105 <= lower <= upper <= 105
 * 题目数据保证答案是一个 32 位 的整数
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *  
 * @author JasonC5
 */
public class LC327 {
    //假设0-i整体的前缀和是x，题目目标是[lower,upper]
    //必须以i结尾的子数组，目标有多少个在[lower,upper]上
    //等同于求i之前的所有前缀和中，有多少个在范围：[x-upper, x-lower]中

    //arr[] 处理成一个前缀和数组：x之前有多少个数在[x-upper,x-lower]范围内

    //右边每个数x，求左边有多少个数在满足条件的范围[x-upper,x-lower]内,两个指针，由于数组是有序的，越来越大，两个指针也只会越来越大，是不会回退的
    //算完之后，再进行merge，让合并数组再变得有序

    //
    public static void main(String[] args) {
        int[] arr = {1,1,1,2,2,2,3,3,3,4,4,4,8};
        int lower = 3;
        int upper = 10;
        //对数器
        int checkCount = countRangeSum(arr, lower, upper);
        System.out.println(checkCount);

//        if (arr == null || arr.length == 0) {
//            return 0;
//        }
        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i-1] + arr[i];
        }
        int count = count(sum, lower, upper, 0, arr.length - 1);
        System.out.println(count);
    }

    public static int count(long[] arr, int lower, int upper,int left, int right) {
        if (left == right){
            //自己满足
            if (arr[left] <= upper && arr[left] >= lower){
                return 1;
            } else {
                return 0;
            }
        }
        //窗口中不止一个元素
        int mid = left + ((right - left) >> 1);
        return count(arr, lower, upper, left, mid) + count(arr, lower, upper, mid+1, right) + merge(arr, lower, upper, left, mid, right);
    }

    public static int merge(long[] arr, int lower, int upper,int left, int mid, int right){
        //右半部分从左半部分中找有多少个满足条件的
        int count = 0;
        int lowPoint=left,highPoint=left;
        for (int i = mid+1;i<=right;i++){
            //遍历右半边，每一个数【前缀和】找到左半边有多少个满足条件的数
            //移动左指针，到合适的位置[ arr[i]-upper, arr[i]-lower ]
            long min = arr[i]-upper;
            long max = arr[i]-lower;
            while(lowPoint <= mid && arr[lowPoint] < min){
                lowPoint++;
            }
            while(highPoint <= mid && arr[highPoint] <= max){
                highPoint++;
            }
            count += highPoint - lowPoint;
        }
        //然后再排序（就之前的排序）
        long[] tmp = new long[right-left+1];
        int leftPoint=left,rightPoint=mid+1,tempPoint=0;
        while (leftPoint <= mid && rightPoint<= right){
            tmp[tempPoint++] = arr[leftPoint] <= arr[rightPoint] ? arr[leftPoint++] : arr[rightPoint++];
        }
        while (leftPoint <= mid) {
            tmp[tempPoint++] =  arr[leftPoint++];
        }
        while (rightPoint <= right) {
            tmp[tempPoint++] =  arr[rightPoint++];
        }
        //放回到对应位置
        for (int i = 0; i < right - left + 1; i++) {
            arr[left+i] = tmp[i];
        }
        return count;
    }


    //对数器
    public static int countRangeSum(int[] nums, int lower, int upper) {
        long s = 0;
        long[] sum = new long[nums.length + 1];
        for (int i = 0; i < nums.length; ++i) {
            s += nums[i];
            sum[i + 1] = s;
        }
        return countRangeSumRecursive(sum, lower, upper, 0, sum.length - 1);
    }

    public static int countRangeSumRecursive(long[] sum, int lower, int upper, int left, int right) {
        if (left == right) {
            return 0;
        } else {
            int mid = (left + right) / 2;
            int n1 = countRangeSumRecursive(sum, lower, upper, left, mid);
            int n2 = countRangeSumRecursive(sum, lower, upper, mid + 1, right);
            int ret = n1 + n2;

            // 首先统计下标对的数量
            int i = left;
            int l = mid + 1;
            int r = mid + 1;
            while (i <= mid) {
                while (l <= right && sum[l] - sum[i] < lower) {
                    l++;
                }
                while (r <= right && sum[r] - sum[i] <= upper) {
                    r++;
                }
                ret += r - l;
                i++;
            }

            // 随后合并两个排序数组
            long[] sorted = new long[right - left + 1];
            int p1 = left, p2 = mid + 1;
            int p = 0;
            while (p1 <= mid || p2 <= right) {
                if (p1 > mid) {
                    sorted[p++] = sum[p2++];
                } else if (p2 > right) {
                    sorted[p++] = sum[p1++];
                } else {
                    if (sum[p1] < sum[p2]) {
                        sorted[p++] = sum[p1++];
                    } else {
                        sorted[p++] = sum[p2++];
                    }
                }
            }
            for (int j = 0; j < sorted.length; j++) {
                sum[left + j] = sorted[j];
            }
            return ret;
        }
    }

}
