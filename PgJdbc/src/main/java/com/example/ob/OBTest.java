package com.example.ob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 匠承
 * @Date: 2024/4/23 10:28
 */
public class OBTest {
    static String host = "t5jkecn0xl79c-mi.cn-beijing.oceanbase.aliyuncs.com";

    static String port = "1521";
    static String user = "jiangcheng";
    static String password = "DWzengyao123";

    public static void main(String[] args) {
        try {

            Class.forName("com.oceanbase.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:oceanbase://" + host + ":" + port + "/?pool=false&user=" + user + "&password=" + password);
            System.out.println(connection.getAutoCommit());
            Statement sm = connection.createStatement();
            //新建表 t_meta_form
            sm.executeUpdate("CREATE TABLE t_meta_form (name varchar(36) , id int)");
            //插入数据
            sm.executeUpdate("insert into t_meta_form values ('an','1')");
            //查询数据，并输出结果
            ResultSet rs = sm.executeQuery("select * from t_meta_form");
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("id");
                System.out.println(name + ',' + id);
            }
            //删除表
            sm.executeUpdate("drop table t_meta_form");
        } catch (SQLException ex) {
            System.out.println("error!");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
