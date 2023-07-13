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


    public static String lsnHexToDec(String lsnInHex) {
        Lsn lsn = Lsn.valueOf(lsnInHex);
        return String.valueOf(lsn.asLong());
    }
}
