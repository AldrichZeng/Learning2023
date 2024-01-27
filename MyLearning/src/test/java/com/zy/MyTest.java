package com.zy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/11/9 16:12
 */
public class MyTest {

    String accessId = "*";
    String accessKey = "*";
    String dbClusterId = "am-8vbp44d4514i7f482";

    private IAcsClient getTmpAkClient(String ownerId, String region) {
        DefaultProfile profile = DefaultProfile.getProfile(region, accessId, accessKey);
        return new DefaultAcsClient(profile);
    }

    @Test
    public void test() throws ClientException {
        // load ds instance info:1624387842781448,cn-zhangjiakou,am-8vbp44d4514i7f482
        IAcsClient client = getTmpAkClient("1624387842781448", "cn-shanghai");
        String action = "DescribeDBClusterAttribute";
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("adb.aliyuncs.com");
        request.setSysVersion("2019-03-15");
        request.setSysAction(action);
        request.putQueryParameter("RegionId", "cn-shanghai");

        Map<String, String> params = new HashMap<>();
        params.put("DBClusterId", dbClusterId);
        if (params != null) {
            params.forEach(request::putQueryParameter);
        }
        CommonResponse response = client.getCommonResponse(request);
        System.out.println("api response: " + JSON.toJSONString(response));
    }

    private Object toJavaArray(Object val) {
        if (null == val) {
            return null;
        } else if (val instanceof JSONArray) {
            Object[] valArray = ((JSONArray) val).toArray();
            for (int i = 0; i < valArray.length; i++) {
                valArray[i] = this.toJavaArray(valArray[i]);
            }
            return valArray;
        } else {
            return val;
        }
    }

    @Test
    public void test3() {

        //String a = "[[\"1\",\"2\",\"3\"],[\"1\",\"2\",\"3\"],[\"1\",\"2\",\"3\"]]";
        String a = "[\"a\\\\b\",\"a\\b\"]";
        List<Object> values = JSON.parseArray(a, Object.class);
        System.out.println(values);//com.alibaba.fastjson.JSONException: expect '[', but {, pos 1, line 1, column 2{"abc", "xyz"}
        //values.set(0, this.toJavaArray(values.get(0)));
        //for(int i=0;i<values.size();i++){
        //    if(values.get(i) instanceof  String){
        //        String value = (String) values.get(i);
        //        System.out.println(value);
        //        System.out.println(StringUtils.replace(value, "\"", "\\\""));
        //    }
        //}
        values.set(1, "abc");
        System.out.println(values);
    }

    @Test
    public void test2() {
        String a = "[[\"1\",\"2\",\"3\"],[\"1\",\"2\",\"3\"],[\"1\",\"2\",\"3\"]]";
        List<Object> values = JSON.parseArray(a, Object.class);
        System.out.println(values.get(0) instanceof JSONArray);
        Object[] array = ((JSONArray) values.get(0)).toArray();
        System.out.println(array[0]);
        System.out.println(array[0] instanceof JSONArray);

        System.out.println(values);
        // [a, b, c]
    }

    @Test
    public void test4() {
        System.out.println(null instanceof String);

        System.out.println("");
    }

    @Test
    public void test5() {
        String a = "[{\"recordTypeInfos\":[{\"active\":true,\"available\":true,\"defaultRecordTypeMapping\":true,\"developerName\":\"Master\",\"master\":true,\"name\":\"Master\",\"recordTypeId\":\"012000000000000AAA\",\"urls\":{\"layout\":\"/services/data/v58.0/sobjects/Contract/describe/layouts/012000000000000AAA\"}}]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[{\"active\":true,\"available\":true,\"defaultRecordTypeMapping\":true,\"developerName\":\"Master\",\"master\":true,\"name\":\"Master\",\"recordTypeId\":\"012000000000000AAA\",\"urls\":{\"layout\":\"/services/data/v58.0/sobjects/Order/describe/layouts/012000000000000AAA\"}}]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]},{\"recordTypeInfos\":[]}]";
        List<Object> list = JSON.parseArray(a, Object.class);
        System.out.println(list);
        System.out.println(list.size());

        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            System.out.println(value);
            if (value instanceof JSONObject){
                String res = StringUtils.replace(value.toString(), "\"", "\\\"");
                System.out.println(res);
            }
        }
    }

    /**
     * JSONArray中的空元素，被字符串打印出来的时候是显示为文本null
     */
    @Test
    public void test6(){
        String a = "[1,null,2]";
        List<Object> list = JSON.parseArray(a, Object.class);
        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.get(1) == null);

        StringBuilder x = new StringBuilder();
        x.append("xyz").append(list.get(1)).append("y");
        System.out.println(x.toString());
    }
}


