package com.zy.aspect;

/**
 * @author 匠承
 * @Date: 2024/11/3 16:54
 */
public class Main {

    @MyLog
    public String sourceC() {
        return "你正在访问sourceC资源";
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.sourceC();
    }
}
