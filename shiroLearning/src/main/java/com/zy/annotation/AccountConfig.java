package com.zy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 匠承
 * @Date: 2024/11/7 23:25
 */
// 指定注解的保留策略
@Retention(RetentionPolicy.RUNTIME)  // 运行时保留
// 指定注解可以应用的目标
@Target({ElementType.METHOD, ElementType.FIELD})  // 可以应用于方法或类
@interface AccountConfig {

    // 定义第一个属性 value
    String value() default "";  // 默认为空字符串

    // 定义第二个属性 description
    String description() default "No description provided";  // 默认描述

}
