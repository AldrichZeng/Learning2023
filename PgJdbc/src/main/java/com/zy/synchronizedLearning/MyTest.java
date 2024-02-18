package com.zy.synchronizedLearning;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 匠承
 * @Date: 2024/1/28 11:05
 */
public class MyTest {

    private Map<String, Integer> map;

    public MyTest() {
        this.map = new HashMap<>();
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public void doSomeThing(String key) {
        synchronized (map) {
            int currentValue = map.getOrDefault(key, 0);
            System.out.println(Thread.currentThread().getName() + ", currentValue = " + currentValue);
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
            map.put(key, currentValue + 1);
        }
    }

    public static void main(String[] args) {

        MyTest myTest = new MyTest();
        int n = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(n);

        for (int i = 0; i < n * 3; i++) {
            final int threadId = i;
            executorService.submit(new Runnable() {
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    myTest.doSomeThing("type");
                }
            });
        }
        executorService.shutdown();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        System.out.println("第一次打印:" + myTest.getMap().get("type"));
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        System.out.println("第二次打印:" + myTest.getMap().get("type"));
    }
}
