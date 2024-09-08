package com.example;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/6/2 11:02
 */
public class Normal1 {
    @Test
    public void test1() throws MalformedURLException {
        String jdbcUrl = "jdbc:mysql://myhost:30043/db?aone";
        Pattern URL_PARAMETER = Pattern.compile("(\\/([^\\?]*))?(\\?(.+))*", 32);

        int separator = jdbcUrl.indexOf("//");
        String urlSecondPart = jdbcUrl.substring(separator + 2);

        int paramIndex = urlSecondPart.indexOf('?');

        if (paramIndex != -1) {
            System.out.println("不用比较");
        }

        // 没有问号的情况
        int dbIndex = urlSecondPart.indexOf("/");
        String additionalParameters = urlSecondPart.substring(dbIndex);

        Matcher matcher = URL_PARAMETER.matcher(additionalParameters);
        if (matcher.find()) {
            String db = matcher.group(2);
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
        }

    }
}
