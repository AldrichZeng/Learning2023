package com.zy.annotation;

/**
 * @author 匠承
 * @Date: 2024/11/7 23:37
 */
public class TestClass {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @AccountConfig(value = "id", description = "sid")
    private String id;



}
