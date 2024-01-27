package com.zy.threadLocalLearning;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 匠承
 * @Date: 2024/1/27 20:47
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                System.out.println(DateUtil.parse("2022-10-21 16:34:30"));
            });
        }
        executorService.shutdown();
    }
}
