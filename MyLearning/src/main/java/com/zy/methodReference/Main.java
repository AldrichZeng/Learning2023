package com.zy.methodReference;

import java.util.function.Supplier;

/**
 * @author 匠承
 * @Date: 2024/5/1 23:10
 */
public class Main {

    public static void main(String[] args) {

        test2();
    }

    public static void test1() {
        Supplier<String> stringSupplier = () -> "hello world";
        //String str = stringSupplier.get();
        String str = stringSupplier.get();

        System.out.println(str);
    }

    public static void test2() {
        Supplier<String> stringSupplier = () -> "hello world";
        System.out.println(stringSupplier);
        Hello hello = new Hello(stringSupplier);
        System.out.println(hello.sayHello());
    }

    public static class Hello {
        Supplier<String> mySupplier;

        public Hello(Supplier<String> mySupplier) {
            System.out.println(mySupplier);
            this.mySupplier = mySupplier;
        }

        public String sayHello() {
            return mySupplier.get();
        }
    }

    public static void test3() {
        HelloInterface helloInterface = () -> "hello world";
        String str = helloInterface.sayHello();
        //String str = helloInterface::sayHello;
        System.out.println(str);
    }

    public static interface HelloInterface {
        String sayHello();
    }
}
