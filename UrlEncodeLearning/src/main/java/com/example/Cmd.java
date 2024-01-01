package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author 匠承
 * @Date: 2023/9/21 11:02
 */
public class Cmd {

    public static void main(String[] args) throws InterruptedException, SQLException {
        BasicParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("j", true, "json text");
        CommandLine cl = null;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            System.exit(1);
        }

        Connection conn = doGetconnect(jdbcUrl, username, password);

        Connection updateConn = doGetconnect(jdbcUrl, username, password);

        Statement select = conn.createStatement();
        String updateSql = "update dw_file_version_bak set content =? where id = ?";
        PreparedStatement update = updateConn.prepareStatement(updateSql);

        String sql = "select * from dw_file_version_bak";
        ResultSet rs = select.executeQuery(sql);
        int count = 0;
        while (rs.next()) {
            count++;
            String content = rs.getString("content");
            //System.out.println(content);

            int id = rs.getInt("id");
            JSONObject object = JSON.parseObject(content);
            JSONArray jsonArray = object.getJSONArray("steps");
            JSONObject object1 = (JSONObject) jsonArray.get(0);
            String stepType = object1.getString("stepType");
            JSONObject parameter = object1.getJSONObject("parameter");
            String lineDelimiter = parameter.getString("lineDelimiter");
            String name = object1.getString("name");
            System.out.println("id = " + id);
            System.out.println("stepType=" + stepType);
            System.out.println("name=" + name);
            System.out.println("lineDelimiter=" + StringEscapeUtils.escapeJava(lineDelimiter));
            System.out.println(content);
            System.out.println(object.toString());

            //String origin = StringEscapeUtils.escapeJava(lineDelimiter);
            //String after = origin.replace("\\\\", "\\");
            //parameter.put("lineDelimiter", after);
            //System.out.println("after = " + object);

            //update.setString(1, object.toJSONString());
            //update.setInt(2, id);
            //update.executeUpdate();

            System.out.println("==========");
        }
        System.out.println(count);
    }

    public static String jdbcUrl = "jdbc:mysql://rm-uf6c748m1egblo8938o.mysql.rds.aliyuncs.com:3306/jiangcheng_db";
    public static String username = "jiangcheng";
    public static String password = "DWzengyao123";

    public static Connection doGetconnect(String jdbcUrl, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
