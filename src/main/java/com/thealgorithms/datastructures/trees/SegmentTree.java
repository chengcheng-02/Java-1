package com.thealgorithms.datastructures.trees;
/*
* 线段树是一种二叉搜索树，它是平衡的，但不一定是完全二叉树
*
* */
public class SegmentTree {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9,10};
        SegmentTree segmentTree = new SegmentTree(10,a);
        System.out.println(segmentTree.getSum(1,2));
        segmentTree.update(5,8);
        for (int ar : segmentTree.arr)
            System.out.print(ar+" ");
        System.out.println(segmentTree.getSum(2,8));
        System.out.println(segmentTree.getSum(1,2));
    }
    private int seg_t[];
    private int n;
    private int arr[];

    /* Constructor which takes the size of the array and the array as a parameter*/
    public SegmentTree(int n, int arr[]) {
        this.n = n;
//        换地公式求log2 n
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        int seg_size = 2 * (int) Math.pow(2, x) - 1;

        this.seg_t = new int[seg_size];
        this.arr = arr;
        this.n = n;
        constructTree(arr, 0, n - 1, 0);
    }

    /* A function which will create the segment tree*/
    public int constructTree(int[] arr, int start, int end, int index) {
        if (start == end) {
            this.seg_t[index] = arr[start];
            return arr[start];
        }

        int mid = start + (end - start) / 2;
        this.seg_t[index] = constructTree(arr, start, mid, index * 2 + 1)
                + constructTree(arr, mid + 1, end, index * 2 + 2);
        return this.seg_t[index];
    }

    /* A function to update the value at a particular index*/
    public void update(int index, int value) {
        if (index < 0 || index > n) {
            return;
        }

        int diff = value - arr[index];
        arr[index] = value;
        updateTree(0, n - 1, index, diff, 0);
    }

    /* A function which will update the value at a index i. This will be called by the
    update function internally*/
    private void updateTree(int start, int end, int index, int diff, int seg_index) {
        if (index < start || index > end) {
            return;
        }

        this.seg_t[seg_index] += diff;
        if (start != end) {
            int mid = start + (end - start) / 2;
//           左子树
            updateTree(start, mid, index, diff, seg_index * 2 + 1);
            updateTree(mid + 1, end, index, diff, seg_index * 2 + 2);
        }
    }



    /* A function to get the sum of the elements from index l to index r. This will be called internally*/
    private int getSumTree(int start, int end, int q_start, int q_end, int seg_index) {
        if (q_start <= start && q_end >= end) {
            return this.seg_t[seg_index];
        }

        if (q_start > end || q_end < start) {
            return 0;
        }

        int mid = start + (end - start) / 2;
        return getSumTree(start, mid, q_start, q_end, seg_index * 2 + 1) + getSumTree(mid + 1, end, q_start, q_end, seg_index * 2 + 2);
    }

    /* A function to query the sum of the subarray [start...end]*/
    public int getSum(int start, int end) {
        if (start < 0 || end > n || start > end) {
            return 0;
        }
        return getSumTree(0, n - 1, start, end, 0);
    }
}
