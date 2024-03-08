package com.example.pgjdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.TimeZone;

import org.postgresql.jdbc.PgArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/2/16 15:28
 */
public class CheckIdentity {
    private static final Logger logger = LoggerFactory.getLogger(TimeTest.class);

    static String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
    static String user = "jctest";
    static String password = "DWzengyao1234";

    public static void main(String[] args) {
        try {
            // 加载驱动程序
            Class.forName("org.postgresql.Driver");
            // 建立连接
            Connection conn = DriverManager.getConnection(url, user, password);
            logger.info("连接上了");

            String sql = "select relreplident from pg_class where oid in "
                    + " (SELECT oid FROM pg_class WHERE relname = ANY(?) AND relnamespace = "
                    + " (SELECT oid FROM pg_namespace WHERE nspname = ?))";

            PreparedStatement stmt = conn.prepareStatement(sql);

            String[] tables = {"test1", "test2", "TEST2"};
            //stmt.setString(1,"test1");
            //stmt.setString(2,"test2");
            stmt.setArray(1, conn.createArrayOf("text", tables));
            stmt.setString(2, "myschema");


            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }

            // 关闭资源
            //rs.close();
            stmt.close();

            logger.info("end");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error");
        }
    }
}
