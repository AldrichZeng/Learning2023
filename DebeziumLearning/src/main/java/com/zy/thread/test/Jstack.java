package com.zy.thread.test;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

/**
 * @author 匠承
 * @Date: 2023/8/26 16:21
 */
public class Jstack {
    public static void main(String[] args) throws InterruptedException {
        final Thread myThread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (this) {
                    System.out.println("in:" + Thread.currentThread());
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }; // 到这一步，线程的状态是NEW
        // 给线程起个名字，方便排查问题
        //myThread.setName("测试线程");
        // 到这一步，线程的状态是RUNNABLE、
        myThread.start();

        synchronized (myThread) {
            System.out.println("out:" + Thread.currentThread());
            TimeUnit.SECONDS.sleep(60);
        }
    }
}
