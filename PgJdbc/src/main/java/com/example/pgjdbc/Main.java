package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

            Thread1 thread1 = new Thread1(conn);
            Thread2 thread2 = new Thread2(conn);

            ExecutorService executor = Executors.newFixedThreadPool(4);

            Future<Boolean> future1 = executor.submit(thread1);
            Future<Boolean> future2 = executor.submit(thread2);
            future1.get();
            future2.get();

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

            String sql = "SELECT * FROM test.thread1 order by id";
            for (int i = 0; i < 10000; i++) {

                // 执行查询
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                // 处理结果
                List<String> result = new ArrayList<>();
                while (rs.next()) {
                    String meg = "id: " + rs.getInt(1) + ", col1: " + rs.getString(2);
                    //logger.info(meg);
                    result.add(meg);
                }
                String res1 = result.stream().collect(Collectors.joining(","));
                if (!"id: 1, col1: thread1-1,id: 2, col1: thread1-2,id: 3, col1: thread1-3,id: 4, col1: thread1-4,id: 5, col1: thread1-5,id: 6, col1: thread1-6,id: 7, col1: thread1-7".equals(res1)) {
                    throw new IllegalArgumentException(res1);
                }

                if (i % 100 == 0) {
                    logger.info("done {}", i);
                }

                // 关闭资源
                rs.close();
                stmt.close();
            }

            return true;
        }
    }

    public static class Thread2 implements Callable<Boolean> {
        Connection conn;

        public Thread2(Connection connection) {
            this.conn = connection;
        }

        @Override
        public Boolean call() throws Exception {
            //Thread.sleep(1000);
            logger.info("thread2 start.");

            String sql = "SELECT * FROM test.thread2 order by id";
            for (int i = 0; i < 10000; i++) {
                // 执行查询
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                List<String> result = new ArrayList<>();
                // 处理结果
                while (rs.next()) {
                    String msg = "id: " + rs.getInt(1) + ", thread2_col1: " + rs.getString(2);
                    //logger.info(msg);
                    result.add(msg);
                    //Thread.sleep(1000);
                }

                String res1 = result.stream().collect(Collectors.joining(","));
                if (!"id: 1, thread2_col1: thread2-1,id: 2, thread2_col1: thread2-2,id: 3, thread2_col1: thread2-3,id: 4, thread2_col1: thread2-4,id: 5, thread2_col1: thread2-5,id: 6, thread2_col1: thread2-6,id: 7, thread2_col1: thread2-7".equals(res1)) {
                    throw new IllegalArgumentException(res1);
                }

                if (i % 100 == 0) {
                    logger.info("done {}", i);
                }

                // 关闭资源
                rs.close();
                stmt.close();
            }
            return true;
        }
    }
}
