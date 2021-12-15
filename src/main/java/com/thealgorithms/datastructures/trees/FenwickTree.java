package com.thealgorithms.datastructures.trees;

import java.util.Scanner;

/*
* 树状数组（二叉索引树）
* 所有的整数都可以表示成2的幂和，我们也可以把一串序列表示成一系列子序列的和
* C[i] = A[i - 2^k+1] + A[i - 2^k+2] + ... + A[i];   //k为i的二进制中从最低位到高位连续零的长度
* SUMi = C[i] + C[i-2k1] + C[(i - 2k1) - 2k2] + ……
* 2^k = i & (-i)
* */
public class FenwickTree {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] source = {1,2,3,4,5,6,7,8,9,10};
        FenwickTree fenwickTree = new FenwickTree(10);
        for (int i = 0 ;i< fenwickTree.n;i++){
            int x = scan.nextInt();
            fenwickTree.update(i,x);
        }
        System.out.println(fenwickTree.query(5));
        scan.close();
    }
//    树的节点个数
    private int n;
    private int fen_t[];

    /* Constructor which takes the size of the array as a parameter */
    public FenwickTree(int n) {
        this.n = n;
        this.fen_t = new int[n + 1];
    }
    public FenwickTree(int[] arr){
        n = arr.length;
        fen_t = arr;
    }
    public int lowbit(int x){
        return x & (-x);
    }
    /* A function which will add the element val at index i
    *  fen_t[i],包含在fen_t[i+2^k]、fen_t[i+2^k+2^k……]上
    * */
    public void update(int i, int val) {
        // As index starts from 0, increment the index by 1
        i += 1;
        while (i <= n) {
            fen_t[i] += val;
//            i += i & (-i);
            i += lowbit(i);
        }
    }

    /* A function which will return the cumulative sum from index 1 to index i
    *  求树状数组的到i和
    * */
    public int query(int i) {
        // As index starts from 0, increment the index by 1
        i += 1;
        int cumSum = 0;
        while (i > 0) {
            cumSum += fen_t[i];
            i -= lowbit(i);
        }
        return cumSum;
    }
}
