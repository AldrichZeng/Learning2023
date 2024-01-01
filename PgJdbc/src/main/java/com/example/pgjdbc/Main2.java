package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/10/20 15:56
 */
public class Main2 {
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

            Thread1 thread1 = new Thread1(conn);

            ExecutorService executor = Executors.newFixedThreadPool(4);

            Future<Boolean> future1 = executor.submit(thread1);
            future1.get();

            logger.info("end");

            conn.close();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error");
        }
    }

    public static class Thread1 implements Callable<Boolean> {

        Connection conn;

        public Thread1(Connection connection) {
            this.conn = connection;
        }

        @Override
        public Boolean call() throws Exception {

            //Thread.sleep(1000);
            logger.info("thread1 start.");

            String querySql = "select * from public.bigintarray_test2 where 1=2";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(querySql);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                logger.info("columnName = {}", rsMetaData.getColumnName(i));
                logger.info("columnType = {}", rsMetaData.getColumnType(i));
                logger.info("columnTypeName = {}", rsMetaData.getColumnTypeName(i));
            }

            System.exit(-1);
            String sql = "insert into public.jsonb_test values (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < 10; i++) {

                // 执行查询
                stmt.setInt(1, i);
                PGobject pgObj = new PGobject();
                pgObj.setType("text");
                pgObj.setValue("{}");
                stmt.setObject(2, pgObj);
                int row = stmt.executeUpdate();

                // 处理结果
                logger.info("row = {}", row);
            }

            stmt.close();
            return true;
        }
    }
}
