package com.zy;

import io.debezium.connector.postgresql.connection.Lsn;

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
