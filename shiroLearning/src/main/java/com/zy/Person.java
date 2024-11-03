package com.zy;

import java.lang.reflect.Field;

/**
 * @author 匠承
 * @Date: 2024/11/3 16:28
 */
public class Person {
    @Range(min = 1, max = 20)
    public String name;

    @Range(max = 10)
    public String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
        // 遍历所有Field:
        for (Field field : person.getClass().getFields()) {
            // 获取Field定义的@Range:
            Range range = field.getAnnotation(Range.class);
            // 如果@Range存在:
            if (range != null) {
                // 获取Field的值:
                Object value = field.get(person);
                // 如果值是String:
                if (value instanceof String) {
                    String s = (String) value;
                    // 判断值是否满足@Range的min/max:
                    if (s.length() < range.min() || s.length() > range.max()) {
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }
                }
                System.out.println("pass");
            } else {
                System.out.println("no range");
            }
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        Person person = new Person();
        person.setName("zengyao");
        person.setCity("hangzhou");
        person.check(person);
    }
}
