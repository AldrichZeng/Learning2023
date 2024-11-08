package com.zy.annotation;

import java.lang.reflect.Field;
import java.util.Base64;

/**
 * @author 匠承
 * @Date: 2024/11/7 23:24
 */
public class Main {

    @AccountConfig(value = "hello", description = "hello")
    public static String masterId;

    public String getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    @AccountConfig(value = "hi", description = "hello")
    private String masterKey;

    public static void main(String[] args) throws IllegalAccessException {
        Main myClass = new Main();
        injectValues(myClass);
        System.out.println(masterId);
        System.out.println(myClass.getMasterKey());

        System.out.println(new String(Base64.getDecoder().decode("TFRBSTV0TEdNaW1RRm5XR2MzcFFyWkNL")));

    }

    public static void injectValues(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("filed: " + field.getName());
            if (field.isAnnotationPresent(AccountConfig.class)) {
                AccountConfig myValue = field.getAnnotation(AccountConfig.class);
                field.setAccessible(true);  // 确保可以访问私有字段
                try {
                    // 假设所有字段都是String类型
                    field.set(obj, myValue.value());
                } catch (IllegalArgumentException e) {
                    System.out.println("无法为 " + field.getName() + " 设置值: " + e.getMessage());
                }
            }
        }
    }
}
