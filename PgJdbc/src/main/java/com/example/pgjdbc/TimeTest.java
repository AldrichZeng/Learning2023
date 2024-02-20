package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/2/16 15:28
 */
public class TimeTest {
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

            String sql = "insert into public.time_test values (?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, 6);

            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
            Timestamp timestamp = new Timestamp(-2209017943000L);
            stmt.setTimestamp(2, timestamp);

            stmt.executeUpdate();

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
