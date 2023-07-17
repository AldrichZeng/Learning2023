package com.zy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import io.debezium.connector.postgresql.connection.Lsn;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 匠承
 * @Date: 2023/7/13 09:59
 */
public class LSNTestTest {

    @Test
    public void test1(){
        Lsn lsn = Lsn.valueOf(1161268569152L);
        System.out.println(lsn.asString());
        System.out.println(lsn.asLong());

        System.out.println("==================");
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(1159741874432L);
        buf.position(0);
        System.out.println(buf.position());
        int a = buf.getInt();
        System.out.println(a);
        System.out.println(String.format("%X", a));
        System.out.println(buf.position());
        int b = buf.getInt();
        System.out.println(b);
        System.out.println(String.format("%X", b));
    }

    @Test
    public void test2() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("startPosition.json");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String initialPosition = sb.toString();
        br.close();

        System.out.println(initialPosition);
    }

    @Test
    public void test3(){
        Lsn lsn = Lsn.valueOf(1161302121872L);
        System.out.println(lsn.asString());
        System.out.println(lsn.asLong());

        lsn = Lsn.valueOf("112/AB001938");
        System.out.println(lsn.asLong());
        System.out.println(lsn.asString());
    }

    // 1160513594416
    @Test
    public void test4(){
        Lsn lsn = Lsn.valueOf("112/A8002E48");
        System.out.println(lsn.asLong());//1161318921736
    }

    @Test
    public void test5(){
        Lsn lsn = Lsn.valueOf("10F/20002078");
        System.out.println(lsn.asLong());//1162829616128
    }

}