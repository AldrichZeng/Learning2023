package com.zy.threadLocalLearning;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 匠承
 * @Date: 2024/1/27 21:02
 */
public class MyTest {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static String value;

    public String getInstsanceValue() {
        return intanceValue;
    }

    public void setIntanceValue(String intanceValue) {
        this.intanceValue = intanceValue;
    }

    String intanceValue;

    public MyTest() {
        this.intanceValue = "hello";
        this.instanceValueThreadLocal.set("hello");
    }

    ThreadLocal<String> instanceValueThreadLocal = new ThreadLocal<>();

    public String getInstanceValueThreadLocal() {
        return instanceValueThreadLocal.get();
    }

    public void setInstanceValueThreadLocal(String value) {
        this.instanceValueThreadLocal.set(value);
    }

    public void removeInstanceValueThreadLocal() {
        this.instanceValueThreadLocal.remove();
    }

    /**
     * 使用ThreadLocal
     *
     * @return
     */
    public static String printUsingThreadLocal() {
        String res = "threadName: " + Thread.currentThread().getName() + ", value = " + threadLocal.get();
        System.out.println(res);
        return res;
    }

    /**
     * 不使用ThreadLocal
     *
     * @return
     */
    public static String print() {
        String res = "threadName: " + Thread.currentThread().getName() + ", value = " + MyTest.value;
        System.out.println(res);
        return res;
    }

    public static void main(String[] args) {

        MyTest test = new MyTest();
        int n = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(n);

        //for (int i = 0; i < n; i++) {
        for (int i = 0; i < n * 3; i++) {
            final int threadId = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("threadName:" + Thread.currentThread().getName() + ", set value to: " + threadId);
                    String tmp = "thread " + threadId;
                    //方式1：不使用ThreadLocal，直接赋值
                    //MyTest.value = tmp;
                    //方式2：通过ThreadLocal赋值
                    //if (threadLocal.get() == null) {
                    //    System.out.println("threadName:" + Thread.currentThread().getName() + ", successfully set value to: " + threadId);
                    //    threadLocal.set(tmp);
                    //}

                    //方式3：验证实例变量是否线程安全
                    //test.setIntanceValue(tmp);
                    //方式4：验证实例变量ThreadLoca是否线程安全
                    if (test.getInstanceValueThreadLocal() == null) {
                        test.setInstanceValueThreadLocal(tmp);
                    }
                    try {
                        // 加上sleep，可以发现静态变量会被不同线程修改，使得各个线程得到了非预期的结果。
                        Thread.sleep(100);
                    } catch (Exception e) {

                    }
                    //方式1：不使用ThreadLocal，直接打印静态变量
                    //String result = print();
                    //方式2：使用ThreadLocal
                    //String result = printUsingThreadLocal();
                    //方式3：验证实例变量是否线程安全
                    //String result = test.getInstsanceValue();
                    //方式4：验证实例变量ThreadLocal是否线程安全
                    String result = test.getInstanceValueThreadLocal();
                    int x = Integer.valueOf(result.substring(result.lastIndexOf("thread ") + 7));
                    System.out.println("threadName:" + Thread.currentThread().getName() + ",预期threadId" + threadId + ",实际threadId:" + x);
                    System.out.println(threadId == x);
                    //threadLocal.remove();
                    test.removeInstanceValueThreadLocal();
                }
            });
        }

        executorService.shutdown();
    }
}
