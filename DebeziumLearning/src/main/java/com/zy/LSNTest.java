package com.zy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import io.debezium.connector.postgresql.connection.Lsn;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 匠承
 * @Date: 2023/7/12 17:50
 */
public class LSNTest {
    public static void main(String[] args) throws IOException {
        Lsn lsn = Lsn.valueOf("10E/3C00AE20");
        System.out.println(lsn.asLong());
        System.out.println(lsn.asString());
        
    }
    public static void test2() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("startPosition.json");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        String initialPosition = sb.toString();
        br.close();

        System.out.println(initialPosition);
    }
    
    public static void test(){
        Lsn lsn = Lsn.valueOf(1159741874432L);
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
}
