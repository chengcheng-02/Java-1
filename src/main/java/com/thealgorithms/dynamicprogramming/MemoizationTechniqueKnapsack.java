package com.thealgorithms.dynamicprogramming;
// Here is the top-down approach of
// dynamic programming

public class MemoizationTechniqueKnapsack {

    /**
     *
     * @param W 背包容量
     * @param wt 物品重量
     * @param val 物品价值
     * @return int 背包价值
     */

    private static int knapSack(int W, int[] wt, int[] val) {

        // Declare the table dynamically
        int size = wt.length;
        if (size == 0 || W <= 0)
            return 0;
        int[][] dp = new int[size][W + 1];
        for (int i = 0; i < W + 1; i++) {
            dp[0][i] = wt[0] <= i ? val[0] : 0;
        }
        for (int i = 1;i < size;i++)
            for (int j = 0 ;j <= W;j++){
                if (wt[i] <= j)
                    dp[i][j] = Math.max(dp[i-1][j],val[i]+dp[i-1][j - wt[i]]);
                else
                    dp[i][j] = dp[i-1][j];
            }
        return dp[size-1][W];
    }

//    改进的动态规划,优化了空间复杂度为O(W)
    private static int knapSack1(int W, int[] wt, int[] val) {

        int size = wt.length;
        if (size == 0 || W <= 0)
            return 0;
        // Declare the table dynamically, 后面的只依赖前面一行，我们只用二行记录，交替使用
        int[][] dp = new int[2][W + 1];
//        初始化
        for (int i = 0;i < W+1;i++) // 我们只需要初始化第一行
            dp[0][i] = wt[0] <= i ? val[0] : 0;
        for (int i = 1 ;i < size;i++) //偶数行用奇数行，奇数行用偶数行
            for (int j = 0;j < W+1;j++){
                dp[i%2][j] = dp[(i-1)%2][j];
                if (j >= wt[i])
                    dp[i%2][j] = Math.max(dp[(i-1)%2][j],dp[(i-1)%2][j-wt[i]]+val[i]);
            }
        return dp[(size-1)%2][W];
}

    private static int knapSack2(int W, int[] wt, int[] val) {

        int size = wt.length;
        if (size == 0 || W <= 0)
            return 0;
        // Declare the table dynamically
        int[]dp = new int[W + 1];
//        初始化
        for (int i = 0;i < W+1;i++)
            dp[i] = wt[0] <= i ? val[0] : 0;
        for (int i = 1 ;i < size;i++)
            for (int j = W;j >= wt[i];j--){
//                从右往左覆盖，
                dp[j] = Math.max(dp[j],dp[j-wt[i]]+val[i]);
            }
        return dp[W];
    }
    // Driver Code
    public static void main(String[] args) {
        int[] val = {60, 100, 120};
        int[] wt = {10, 20, 30};

        int W = 50;

        System.out.println(knapSack2(W, wt, val));
    }
}
