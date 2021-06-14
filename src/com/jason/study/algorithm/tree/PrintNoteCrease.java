package com.jason.study.algorithm.tree;

/**
 * ��ӡֽ���ۺ�--΢��������
 * һ��ֽ�����϶���n�Σ����ϵ��´�ӡ�ۺۡ�����͹��
 * ��һ�ζ��ۣ���
 * �ڶ��ζ��ۣ��� �� ͹
 * �����ζ��ۣ��� �� ͹ �� �� ͹ �� ͹ ��
 * ÿһ�ζ��ۣ�����֮ǰ���ۺ��������һ�����ۺۣ��������һ��͹�ۺ�
 * @author JasonC5
 */
public class PrintNoteCrease {
    public static void main(String[] args) {
        printCase(4);
    }

    private static void printCase(int n) {
        //i��Ϊ����
        //��һ��ֻ��һ���ڵ㣺��
        //һ��ÿ���ڵ�����Ӻ����ǰ������Ӻ�����͹
        //�����ۺ۵Ĵ�ӡ��������ôһ�����������������
        //�ݹ�ʵ��
        printProcess(1,n,"��");
    }

    /**
     *
     * @param i ��ǰ����
     * @param n �ܹ�����
     * @param b ��ǰ�ڵ��ǰ�����͹
     */
    private static void printProcess(int i, int n, String b) {
        if (i > n){
            return;
        }
        printProcess(i+1, n, "��");
        System.out.print(b + " ");
        printProcess(i+1, n, "͹");
    }
}
