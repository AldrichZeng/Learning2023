package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang3.StringEscapeUtils;

import static com.example.Cmd.jdbcUrl;
import static com.example.Cmd.password;
import static com.example.Cmd.username;

/**
 * @author 匠承
 * @Date: 2023/9/22 11:54
 */
public class Update {
    public static void main(String[] args) throws SQLException {

        String a = "aaa\\u0001nbbb";
        // 一次转义后
        System.out.println(a);
        // 不转义
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b);

        String c = b.replace("\\\\", "\\");
        System.out.println(c);

        Connection conn = Cmd.doGetconnect(jdbcUrl, username, password);
        //String sql = "insert into dw_file_version_bak values (?,?)";
        String sql = "update dw_file_version_bak set content = ? where id = ?";
        PreparedStatement update = conn.prepareStatement(sql);
        //update.setLong(1, 4);
        //update.setString(2, c);
        update.setString(1, c);
        update.setInt(2, 5);
        update.executeUpdate();

        update.close();
        conn.close();
    }
}
