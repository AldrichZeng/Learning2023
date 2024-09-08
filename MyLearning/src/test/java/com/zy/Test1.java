package com.zy;

import com.aliyun.dataworks_public20240518.models.CreateDataSourceRequest;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author 匠承
 * @Date: 2024/8/13 23:18
 */
public class Test1 {

    @Test
    public void test1(){
        String a = "aaa";
        String b = "ccc";

        BeanUtils.copyProperties(a,b);
        System.out.println(b);
    }

    @Test
    public void test2(){
        A a = new A();
        a.setA("aaa");
        B b = new B();
        BeanUtils.copyProperties(a,b);
        System.out.println(b);
    }
    @Test
    public void test3(){
        B b = new B();
        b.setA(new JSONObject(){
            {
                put("xxx", "xxx");
            }
        });
        A a = new A();
        BeanUtils.copyProperties(b,a);
        System.out.println(a);
    }

    @Data
    public static class A {
        private String a;
    }

    @Data
    public static class B{
        private JSONObject a;
    }
    @Test
    public void test4(){
        CreateDataSourceRequest request=new CreateDataSourceRequest();
        request.setDescription("xxx");
    }

    @Test
    public void test5(){
        String a = "萙\uE085\uD846\uDE6Cꙛ撢궘涏矤顃꤈쭁⧈\uD843\uDC09\uF051⳨ᢃ\uF2C3˛魯ῗ迕皓♱븣贾咧薨ᷲ";
        System.out.println(a);



    }
}

