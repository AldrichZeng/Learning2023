package com.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/4/23 17:25
 */
public class selectDb {
    @Test
    public void test() throws IOException {
        HttpPut httpPut = new HttpPut("http://139.224.229.149:47546/copy/upload");
        Map<String, String> header = new HashMap<>();
        header.put("fileName", "dir1/data.csv");
        header.put("Authorization","Basic YWRtaW46KioqKg==");
        httpPut.setEntity(new StringEntity(""));

        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .disableRedirectHandling();
        CloseableHttpResponse execute = httpClientBuilder.build().execute(httpPut);
    }

    @Test
    public void test2(){
        String s = "hello   world"; // 包含制表符
        String unescaped = StringEscapeUtils.unescapeJava(s);
        System.out.println(unescaped);
    }
}
