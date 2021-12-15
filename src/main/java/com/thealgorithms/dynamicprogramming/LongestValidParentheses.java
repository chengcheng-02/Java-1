package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Given a string containing just the characters '(' and ')', find the length of
 * the longest valid (well-formed) parentheses substring.
 * 给定一个括号序列A，求其最长合法括号子串的长度。
 *
 * 考虑一个括号序列合法的充要条件。它含两个条件：
 * 1、左右括号个数相等；
 * 2、任意前缀中左括号个数不小于右括号个数。
 *
 * @author Libin Yang (https://github.com/yanglbme)
 * @since 2018/10/5
 */
public class LongestValidParentheses {

    public static int getLongestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int n = chars.length;
        int[] res = new int[n];
        res[0] = 0;
        res[1] = chars[1] == ')' && chars[0] == '(' ? 2 : 0;

        int max = res[1];

        for (int i = 2; i < n; ++i) {
            if (chars[i] == ')') {
                if (chars[i - 1] == '(') {
                    res[i] = res[i - 2] + 2;
                } else {
                    int index = i - res[i - 1] - 1;
                    if (index >= 0 && chars[index] == '(') {
                        // ()(())
                        res[i] = res[i - 1] + 2 + (index - 1 >= 0 ? res[index - 1] : 0);
                    }
                }
            }
            max = Math.max(max, res[i]);
        }

        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String str = sc.nextLine();
            if ("quit".equals(str)) {
                break;
            }

            System.out.println("Len is: " + getLongestValidParentheses(str));
        }

        sc.close();
    }
}
