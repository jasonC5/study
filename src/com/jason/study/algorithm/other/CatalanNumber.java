package com.jason.study.algorithm.other;

/**
 * @author JasonC5
 */
public class CatalanNumber {
//	k(0) = 1, k(1) = 1
//
//	k(n) = k(0) * k(n - 1) + k(1) * k(n - 2) + ... + k(n - 2) * k(1) + k(n - 1) * k(0)
//	或者
//	k(n) = c(2n, n) / (n + 1)
//	或者
//	k(n) = c(2n, n) - c(2n, n-1)

    public static long fun1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        long[] k = new long[n + 1];
        k[0] = 1;
        k[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int left = 0; left < i; left++) {
                k[i] += k[left] * k[i - 1 - left];
            }
        }
        return k[n];
    }

    public static long num2(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }
        long a = 1;
        long b = 1;
        for (int i = 1, j = N + 1; i <= N; i++, j++) {
            a *= i;
            b *= j;
            long gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        return (b / a) / (N + 1);// k(n) = c(2n, n) / (n + 1)--看不懂
    }

    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 15; i++) {
            long ans1 = fun1(i);
            long ans2 = num2(i);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
