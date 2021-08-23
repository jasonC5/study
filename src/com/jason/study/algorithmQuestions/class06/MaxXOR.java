package com.jason.study.algorithmQuestions.class06;

public class MaxXOR {

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    private static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] xorSum = new int[arr.length];
        int ans = xorSum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            xorSum[i] = xorSum[i - 1] ^ arr[i];
        }
        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
                ans = Math.max(ans, i == 0 ? xorSum[j] : xorSum[j] ^ xorSum[i - 1]);
            }
        }
        return ans;
    }

    public static class Node {
        Node[] son = new Node[2];
    }

    //前缀树
    public static class PrefixTree {
        Node root = new Node();

        public void add(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; i--) {
                int next = 1 & (num >> i);
                cur.son[next] = cur.son[next] == null ? new Node() : cur.son[next];
                cur = cur.son[next];
            }
        }

        public int maxXor(int num) {
            //最想要的：符号位相同（异或完是正数），其他没位都不同
            int want = (num & (1 << 31)) | ((~num) & 0x7fffffff);
            int ans = 0;
            Node cur = root;
            for (int i = 31; i >= 0; i--) {
                int numi = ((num >> i) & 1);//num的第i+1位
                int curWant = (want >> i) & 1;//第i+1位，想要的
//                int curWant = i == 31 ? numi : (1 ^ numi);
                int actual = cur.son[curWant] == null ? (1 ^ curWant) : curWant;//现实能得到的
                cur = cur.son[actual];//下个位置
                ans |= (numi ^ actual) << i;
            }
            return ans;
        }
    }

    private static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        PrefixTree prefixTree = new PrefixTree();
        prefixTree.add(0);
        int ans = Integer.MIN_VALUE;
        // 0~i整体异或和
        int xor = 0;
        for (int num : arr) {
            xor ^= num;
            ans = Math.max(ans, prefixTree.maxXor(xor));
            prefixTree.add(xor);
        }
        return ans;
    }


}
