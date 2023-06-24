package com.example.pgjdbc;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/6/6 14:52
 */
public class PgDriverTest {
    @Test
    public void test() throws JSONException {
        JSONObject connection = new JSONObject();
        connection.put("a", "b");
        System.out.println(connection);

    }
}
