package com.xuebusi.xbs;

/**
 * @Author: syj
 * @CreateDate: 2018/7/25 14:43
 */
public class TestApp {
    public static void main(String[] args) {
        String toUpperCase = null;
        try {
            toUpperCase = testEx();
        } catch (Exception e) {
            System.out.println("=========e.toString()=========" + e.toString());
            System.out.println("=========e.getMessage()=========" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(toUpperCase);
    }

    private static String testEx() {
        String str = null;
        String upperCase = str.toUpperCase();
        return upperCase;
    }

}
