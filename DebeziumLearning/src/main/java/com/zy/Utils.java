package com.zy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.debezium.connector.postgresql.connection.Lsn;

/**
 * @author 匠承
 * @Date: 2023/7/12 17:50
 */
public class Utils {

    public static String lsnHexToDec(String lsnInHex) {
        Lsn lsn = Lsn.valueOf(lsnInHex);
        return String.valueOf(lsn.asLong());
    }

    public static String JSONFormat(String s) {
        JSONObject object = JSONObject.parseObject(s);
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }
}
