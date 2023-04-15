package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/4/15 14:27
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
        String user = "jctest";
        String password = "DWzengyao1234";

        try {
            // 加载驱动程序
            Class.forName("org.postgresql.Driver");
            // 建立连接
            Connection conn = DriverManager.getConnection(url, user, password);
            logger.info("连接上了");
            // 创建查询语句
            String sql = "SELECT * FROM public.debezium_test";
            // 执行查询
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 处理结果
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("addr");
                System.out.println("id: " + id + ", addr: " + name);
            }

            // 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
