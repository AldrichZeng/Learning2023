package com.zy;

import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.debezium.connector.postgresql.connection.Lsn;
import org.postgresql.PGConnection;
import org.postgresql.replication.PGReplicationStream;

/**
 * @author 匠承
 * @Date: 2023/9/13 19:36
 */
public class PGstream {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
        String username = "jctest";
        String password = "DWzengyao1234";

        PGConnection pgConnection = (PGConnection) DriverManager.getConnection(url, username, password);

        Properties properties = new Properties();

        PGReplicationStream stream =
                pgConnection
                        .getReplicationAPI()
                        .replicationStream()
                        .logical()
                        .withSlotName("\"di_slot\"")
                        .withStartPosition(Lsn.valueOf(1466026691224L).asLogSequenceNumber())
                        .withSlotOption("proto_version", "1")
                        .withSlotOption("publication_names", "di_pub")
                        .withSlotOption("messages", "true")
                        .withStatusInterval(10000, TimeUnit.MILLISECONDS)
                        .start();

        while (true) {
            ByteBuffer buffer = stream.read();
            System.out.println(buffer);
        }

        //stream

    }
}
