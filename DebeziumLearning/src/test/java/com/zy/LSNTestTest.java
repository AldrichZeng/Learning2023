package com.zy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import io.debezium.connector.postgresql.connection.Lsn;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 匠承
 * @Date: 2023/7/13 09:59
 */
public class LSNTestTest {

    @Test
    public void test7() {
        String a = "{\n"
                + "    \"enginePostions\":\n"
                + "    {\n"
                + "        \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\":\n"
                + "        {\n"
                + "            \"sourceOffset\":\n"
                + "            {\n"
                + "                \"lsn_proc\": 1844084481032,\n"
                + "                \"lsn_commit\": 1844084481032,\n"
                + "                \"lsn\": 1844084481032,\n"
                + "                \"txId\": 7286452,\n"
                + "                \"ts_usec\": 1701335515834435\n"
                + "            },\n"
                + "            \"sourcePartition\":\n"
                + "            {\n"
                + "                \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "            }\n"
                + "        }\n"
                + "    },\n"
                + "    \"engineWindows\":\n"
                + "    {\n"
                + "        \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\":\n"
                + "        [\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844017370256,\n"
                + "                    \"lsn_commit\": 1843732376048,\n"
                + "                    \"lsn\": 1844017370256,\n"
                + "                    \"txId\": 7286190,\n"
                + "                    \"ts_usec\": 1701334369345933\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701334369345\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844034148104,\n"
                + "                    \"lsn_commit\": 1844017376600,\n"
                + "                    \"lsn\": 1844034148104,\n"
                + "                    \"txId\": 7286298,\n"
                + "                    \"ts_usec\": 1701334833858784\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701334833858\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844050923112,\n"
                + "                    \"lsn_commit\": 1844050920336,\n"
                + "                    \"lsn\": 1844050923112,\n"
                + "                    \"txId\": 7286349,\n"
                + "                    \"ts_usec\": 1701335039300867\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701335039300\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844084474336,\n"
                + "                    \"lsn_commit\": 1844050923288,\n"
                + "                    \"lsn\": 1844084474336,\n"
                + "                    \"txId\": 7286451,\n"
                + "                    \"ts_usec\": 1701335514766348\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701335514766\n"
                + "            }\n"
                + "        ]\n"
                + "    }\n"
                + "}";
        PostgresPosition p = JSON.parseObject(a, PostgresPosition.class);
        System.out.println(p);
        Map map = p.getEnginePostions();
        System.out.println(p.getSourceOffset("pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611"));
    }

    @Test
    public void test4() {
        System.out.println(Lsn.valueOf("36/15011638").asLong());
        System.out.println(Lsn.valueOf("2A/FF000F28").asLong());
        System.out.println(Lsn.valueOf("1AD/5C001C08").asLong() < Lsn.valueOf("1AD/63001E30").asLong());
        System.out.println(Lsn.valueOf("1AD/5C001C08").compareTo(Lsn.valueOf("1AD/63001E30")));
    }

    @Test
    public void test100() {
        String a
                = "{\"jzms_test\":{\"mappings\":{\"_doc\":{\"properties\":{\"apiID\":{\"type\":\"keyword\"},\"appId\":{\"type\":\"keyword\"},\"appName\":{\"type\":\"text\"},\"id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"reportTime\":{\"type\":\"keyword\"},\"requestAppId\":{\"type\":\"keyword\"},\"requestAppName\":{\"type\":\"text\"},\"requestData\":{\"type\":\"text\"},\"resultId\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"serviceID\":{\"type\":\"keyword\"},\"serviceStatusList\":{\"properties\":{\"requestAppId\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"requestAppName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"requestData\":{\"properties\":{\"@type\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"prefix\":{\"type\":\"text\","
                + "\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}}},\"serviceID\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"startTime\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}}},\"serviceType\":{\"type\":\"keyword\"},\"startTime\":{\"type\":\"keyword\"}}}}}}";
        JSONObject x = JSON.parseObject(a);
        System.out.println(x);
    }

    @Test
    public void test101() {
        Date currentDate = new Date(); // 获取当前日期和时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(currentDate);
        System.out.println(formattedDate);
        ZoneId zoneId = ZoneId.systemDefault(); // 这里将是通过JVM参数设置的时区
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        System.out.println(now);
    }

    @Test
    public void test() {
        Lsn lsn = Lsn.valueOf(1844285819448L);
        System.out.println(lsn.asString());

        System.out.println(MainSourceRecord.initialPosition);
    }

    @Test
    public void test1() {
        Lsn lsn = Lsn.valueOf(1161268569152L);
        System.out.println(lsn.asString());
        System.out.println(lsn.asLong());

        System.out.println("==================");
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(1159741874432L);
        buf.position(0);
        System.out.println(buf.position());
        int a = buf.getInt();
        System.out.println(a);
        System.out.println(String.format("%X", a));
        System.out.println(buf.position());
        int b = buf.getInt();
        System.out.println(b);
        System.out.println(String.format("%X", b));
    }

    @Test
    public void test2() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("startPosition.json");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String initialPosition = sb.toString();
        br.close();

        System.out.println(initialPosition);
    }

    @Test
    public void test3() {
        Lsn lsn = Lsn.valueOf(1892587408656L);
        //System.out.println(lsn.asString());
        //System.out.println(lsn.asLong());

        lsn = Lsn.valueOf("112/AB001938");
        System.out.println(lsn.asLong());
        System.out.println(lsn.asString());

        System.out.println(Long.parseLong("AB001938", 16));
    }

    @Test
    public void test5() {
        Lsn lsn = Lsn.valueOf("1B8/C90052F8");
        System.out.println(lsn.asLong());//1162829616128
    }

    @Test
    public void test6() {
        AtomicReference<Boolean> successDeleteSlot = new AtomicReference<>(true);
        successDeleteSlot.set(false);
        System.out.println(successDeleteSlot.get());
        if (!successDeleteSlot.get()) {
            System.out.println("hallo");
        }

        AtomicReference<String> str = new AtomicReference<>();
        System.out.println(str);
        Assert.assertNull(str.get());
        str.set("abc");
        System.out.println(str.get());
    }

    @Test
    public void test8() {

        Map<String, Object> map = new HashMap<>();
        String a = null;
        System.out.println(StringUtils.isBlank(a));
        a = "";
        System.out.println(StringUtils.isBlank(a));

        a = a + "";
        System.out.println(a);
        System.out.println(a.length());

        String x = (String) map.get("a");
        System.out.println(x);
        System.out.println(x.length());
    }

    @Test
    public void test19() {
        String jdbcUrl = "jdbc:oceanbase://t534p4fky9fgg.oceanbase.aliyuncs.com:3306/?yearIsDateType=false&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&tinyInt1isBit=false";
        Pattern pattern = Pattern.compile("//([\\w\\.\\-]+:\\d+)/([\\w]+)\\?");
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            System.out.println("hahaha");
        } else {
            System.out.println("abc");
        }
    }

    @Test
    public void test20() {
        String jdbcUrl = "jdbc:postgresql://10.0.0.1:3306/db?a=b";
        System.out.println(jdbcUrl.indexOf("?"));//34
        System.out.println(jdbcUrl.substring(0, 35));
    }

    @Test
    public void test21() {
        String url = "";
        int separator = url.indexOf("//");
        String urlSecondPart = url.substring(separator + 2);
        int dbIndex = urlSecondPart.indexOf("/");
        int paramIndex = urlSecondPart.indexOf("?");
        String hostAddressesString;
        String additionalParameters;
        if ((dbIndex < paramIndex && dbIndex < 0) || (dbIndex > paramIndex && paramIndex > -1)) {
            hostAddressesString = urlSecondPart.substring(0, paramIndex);
            additionalParameters = urlSecondPart.substring(paramIndex);
        } else if (dbIndex < paramIndex || dbIndex > paramIndex) {
            hostAddressesString = urlSecondPart.substring(0, dbIndex);
            additionalParameters = urlSecondPart.substring(dbIndex);
        } else {
            hostAddressesString = urlSecondPart;
            additionalParameters = null;
        }

        parseHost(hostAddressesString);

        Pattern URL_PARAMETER = Pattern.compile("(\\/([^\\?]*))?(\\?(.+))*", Pattern.DOTALL);
        String database;
        Map<String, String> properties = null;
        if (additionalParameters != null) {
            Matcher matcher = URL_PARAMETER.matcher(additionalParameters);
            boolean unused = matcher.find();
            database = matcher.group(2);

            properties = parseProperties(matcher.group(4));
            if (database != null && database.isEmpty()) {
                database = null;
            }
        } else {
            database = null;
        }
        if (database != null && !URL_PARAMETER.matcher(database).matches()) {
            System.out.println("invalid database in url: " + database);
        }
        System.out.println("database:" + database);
        System.out.println("properties:" + properties);
    }

    @Test
    public void test22() {
        String a = "abc:100,abc:100,abc,,,d";
        String[] hostAndPorts = a.split(",");
        for (int i = 0; i < hostAndPorts.length; i++) {
            System.out.println(hostAndPorts[i]);
            System.out.println(i);
        }

        String b = "a,,b,,c";
    }

    private void parseHost(String hostAddressesString) {
        String[] hostAndPorts = hostAddressesString.split(",");

        Pattern HOST_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-\\.:\\[\\],]+$");
        int portIndex = hostAddressesString.lastIndexOf(":");
        int bracketIndex = hostAddressesString.indexOf("]");
        String hostString;
        String portString;
        if ((bracketIndex > -1 && bracketIndex < portIndex) ||
                (portIndex > -1 && hostAddressesString.indexOf(":") == portIndex)) {
            hostString = hostAddressesString.substring(0, portIndex);
            portString = hostAddressesString.substring(portIndex + 1);
        } else {
            hostString = hostAddressesString;
            portString = null;
        }

        if (!HOST_PATTERN.matcher(hostString).matches()) {
            System.out.println("invalid host '" + hostString + "' in url: " + hostAddressesString);
            // throw
        }
        System.out.println(hostString);
        if (portString != null && !portString.isEmpty()) {
            int port = -1;
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                System.out.println("invalid host in url: " + hostAddressesString);
                // throw
            }

            if (port < 0 || port > 65535) {
                System.out.println("invalid port " + portString + " in url: " + portString);
                // throw
            }
        } else {
            System.out.println("port = -1");
        }
    }

    private Map<String, String> parseProperties(String urlParameters) {
        Map<String, String> properties = new HashMap<>();
        if (urlParameters != null && !urlParameters.isEmpty()) {
            String[] parameters = urlParameters.split("&");
            for (String parameter : parameters) {
                int pos = parameter.indexOf('=');
                if (pos == -1) {
                    if (!properties.containsKey(parameter)) {
                        properties.put(parameter.trim(), "");
                    }
                } else {
                    properties.put(parameter.substring(0, pos).trim(), parameter.substring(pos + 1).trim());
                }
            }
        }
        return properties;
    }

    @Test
    public void test0426() {
        String jdbcUrl = "jdbc:mysql://abc:123,asdf:3306/mydb?useSSL=false&serverTimezone=UTC";
        String cleanURI = StringUtils.trim(StringUtils.substringAfter(jdbcUrl, ":"));
        System.out.println(cleanURI);
        URI uri = URI.create(cleanURI);
        System.out.println(uri.getPath());
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());
        String database = StringUtils.removeStart(uri.getPath(), "/");
        System.out.println(database);
        if (StringUtils.isEmpty(database) && StringUtils.isNotEmpty(uri.getQuery())) {
            List<NameValuePair> parsedUrl = URLEncodedUtils.parse(uri.getQuery(), StandardCharsets.UTF_8);
            for (NameValuePair nameValuePair : parsedUrl) {
                if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "SCHEMA")) {
                    database = nameValuePair.getValue();
                }
            }
        }
    }

    @Test
    public void test51() {
        String str = "15:49:34Z";
        LocalDateTime localDateTime =
                LocalDateTime.parse(
                        str.replace("Z", "+00:00"));
        long timestamp = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    @Test
    public void test52() {
        String str = "01:00:00Z";
        LocalTime localTime = LocalTime.parse(str.replace("Z", ""));
        System.out.println(localTime);
        System.out.println(localTime.getNano());
        System.out.println(localTime.getSecond());
        System.out.println(localTime.atOffset(ZoneOffset.UTC));
        OffsetTime time = localTime.atOffset(ZoneOffset.UTC);

        System.out.println("===========");
        LocalDate currentDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(currentDate, localTime);
        long timestamp = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        timestamp = timestamp / 1000L * 1000L;

        System.out.println(timestamp);
        System.out.println(timestamp - LocalDateTime.of(currentDate, LocalTime.of(0, 0, 0)).toInstant(ZoneOffset.systemDefault().getRules().getOffset(localDateTime)).toEpochMilli());

        System.out.println(ZoneOffset.systemDefault().getRules().getOffset(localDateTime));
    }

    @Test
    public void test53() {
        String str = "{\n"
                + "    \"a\": \"123\",\n"
                + "    \"b\": \"456\",\n"
                + "    \"c\":\"123\"\n"
                + "}";

        JSONObject obj = JSONObject.parseObject(str);
        TT tt = obj.toJavaObject(TT.class);
        System.out.println(tt);
    }

    @Test
    public void test54() throws JsonProcessingException {
        //String str = "{\n"
        //        + "    \"a\": \"123\",\n"
        //        + "    \"b\": \"456\",\n"
        //        + "    \"c\":\"123\"\n"
        //        + "}";

        //String str = "{\n"
        //        + "    \"a\": \"123\",\n"
        //        + "    \"b\": \"456\""
        //        + "}";

        String str = "{\n"
                + "    \"a\": \"123\""
                + "}";

        JSONObject obj = JSONObject.parseObject(str);
        ObjectMapper mapper = new ObjectMapper();

        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                // 当遇到未知属性时，抛出自定义异常
                throw new RuntimeException("Unknown property '" + propertyName + "' found in JSON for object of type " + beanOrClass.getClass().getName());
            }
        });
        JsonNode node = mapper.readTree(obj.toString());

        TT tt = mapper.treeToValue(node, TT.class);
        System.out.println(tt);
    }

    @Data
    public static class TT {
        String a;
        String b;
        List<Item> list;

        @Data
        public static class Item {
            String x;
            String y;
        }
    }

    @Test
    public void test55() throws JsonProcessingException {
        String str = "{\n"
                + "    \"a\": \"aaa\",\n"
                + "    \"b\": \"bbb\",\n"
                + "    \"list\":\n"
                + "    [\n"
                + "        {\n"
                + "            \"x\": \"xxx\",\n"
                + "            \"y\": \"yyy\"\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        JSONObject obj = JSONObject.parseObject(str);
        ObjectMapper mapper = new ObjectMapper();

        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                // 当遇到未知属性时，抛出自定义异常
                throw new RuntimeException("Unknown property '" + propertyName + "' found in JSON for object of type " + beanOrClass.getClass().getName());
            }
        });
        JsonNode node = mapper.readTree(obj.toString());

        TT tt = mapper.treeToValue(node, TT.class);
        System.out.println(tt);
    }

    @Test
    public void test56() {
        A a = new A();
        a.setMessage("hello");
        System.out.println(a);

        //System.out.println(JSON.toJSONString(a, SerializerFeature.BrowserCompatible));
        System.out.println(JSONObject.parseObject(JSON.toJSONString(a)));
    }

    public static class A {
        @JSONField(name = "Message")
        String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }

    @Test
    public void test57() {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/db?a=b";
        URI uri = URI.create(jdbcUrl.substring(5));
        System.out.println(uri);
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());// 3306
        System.out.println(uri.getPath());//  /db
        System.out.println(uri.getQuery());// a=b
    }

    @Test
    public void test58() {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306,127.0.0.2:3307/db?a=b";
        URI uri = URI.create(jdbcUrl.substring(5));
        System.out.println(uri);
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());// -1
        System.out.println(uri.getHost() == null);
        System.out.println(uri.getPath());//  /db
        System.out.println(uri.getQuery());// a=b
        System.out.println(uri.getRawPath());
        System.out.println(uri.getScheme());

        System.out.println(jdbcUrl.substring(0, jdbcUrl.lastIndexOf(uri.getPath())));
    }

    @Test
    public void test59() {
        String jdbcUrl = "jdbc:oracle:thin:@host:port:SID";
        URI uri = URI.create(jdbcUrl.substring(5));
        System.out.println(uri);
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());// -1
        System.out.println(uri.getPath());//  /db
        System.out.println(uri.getQuery());// a=b
        System.out.println(uri.getRawPath());
        System.out.println(uri.getScheme());

        System.out.println(uri.getPath() == null);

        System.out.println(jdbcUrl.substring(0, jdbcUrl.lastIndexOf(uri.getPath())));
    }

    @Test
    public void test60() {
        String a = "{\n"
                + "\t\"a\":\"122\",\n"
                + "\t\"b\":\"144\",\n"
                + "\t\"y\":\"y\"\n"
                + "}";

        X x = JSONObject.toJavaObject(JSON.parseObject(a), X.class);
        System.out.println(x.toString());

    }

    @Data
    public static class X {
        String a;
        String b;
        Y y;
    }

    public static enum Y {
        y("yy");
        private String str;

        Y(String str) {
            this.str = str;
        }
    }

    @Test
    public void test61(){
        JSONObject obj = JSONObject.parseObject("{\n"
                + "    \"address\":\n"
                + "    [\n"
                + "        {\n"
                + "            \"host\": \"127.0.0.1\",\n"
                + "            \"port\": \"3306\"\n"
                + "        }\n"
                + "    ]\n"
                + "}");
        JSONArray jsonArray = obj.getJSONArray("address");
        List<Address> res = jsonArray.toJavaList(Address.class);
        System.out.println(res);

        JSONObject x = new JSONObject();
        x.put("add", res);
        System.out.println(x);

    }

    @Data
    public static class Address{
        String host;
        String port;
    }
}