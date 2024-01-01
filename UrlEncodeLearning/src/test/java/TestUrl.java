import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.util.UriUtils;

/**
 * @author 匠承
 * @Date: 2023/7/1 16:24
 */
public class TestUrl {

    @Test
    public void testUrl() throws URISyntaxException {
        String testUrl = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253";
        URI uri = new URI(testUrl);

        System.out.println("Scheme: \t" + uri.getScheme());
        System.out.println("Hots: \t" + uri.getHost());
        System.out.println("Raw query: \t" + uri.getRawQuery());

        Assert.assertEquals(uri.getScheme(), "http");
        Assert.assertEquals(uri.getHost(), "www.baeldung.com");
        Assert.assertEquals(uri.getRawQuery(), "key1=value+1&key2=value%40%21%242&key3=value%253");
    }

    @Test
    public void testEncodeUrl() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");
        requestParams.put("key4", "value+4");

        String encodedURL = requestParams.keySet().stream().map(
                new Function<String, String>() {
                    public String apply(String key) {
                        try {
                            return key + "=" + encodeValue(requestParams.get(key));
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).collect(Collectors.joining("&", "http://www.baeldung.com?", ""));

        System.out.println("encodeURL: \t" + encodedURL);
        Assert.assertEquals(encodedURL, "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253&key4=value%2B4");
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    @Test
    public void testDecodeUrl() throws URISyntaxException {
        String testUrl = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253";
        URI uri = new URI(testUrl);

        String query = uri.getRawQuery();
        String decodedQuery = Arrays.stream(query.split("&"))
                .map(new Function<String, String>() {
                    public String apply(String param) {
                        try {
                            return param.split("=")[0] + "=" + decode(param.split("=")[1]);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .collect(Collectors.joining("&"));

        System.out.println(decodedQuery);
        Assert.assertEquals(decodedQuery, "key1=value 1&key2=value@!$2&key3=value%3");
    }

    @Test
    public void testPath() {
        String path = "/Path 1/Path+2";
        String encodedPath = UriUtils.encodePath(path, "UTF-8");
        String decodedPath = UriUtils.decode(encodedPath, "UTF-8");
        System.out.println("encodedPath = " + encodedPath);
        System.out.println("decodedPath = " + decodedPath);
        Assert.assertEquals("/Path%201/Path+2", encodedPath);
        Assert.assertEquals("/Path 1/Path+2", decodedPath);
    }

    @Test
    public void test1() {
        // 模拟313的原始串
        String a = "aaa\\nbbb";
        // 一次转义后
        System.out.println(a);
        // 不转义
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b);

        //String c = StringUtils.escapeQuote(b, "\\");
        //System.out.println(c);
    }

    @Test
    public void test2() {
        // 模拟313的原始串
        String a = "aaa\\u0001nbbb";
        // 一次转义后
        System.out.println(a);
        // 不转义
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b);

        Pattern pattern = Pattern.compile(b, Pattern.LITERAL);
        System.out.println(pattern.pattern());

        String c = b.replace("\\\\", "\\");
        System.out.println(c);
    }

    @Test
    public void test3() {
        // 从数据库中读取到一个String
        String originStr = "{"
                + "\"parameter\":{"
                + "\"lineDelimiter\": \"a\\\\nb\""
                + "}"
                + "}";
        // 在Java中转化为JSON Object
        JSONObject jsonObject = JSON.parseObject(originStr);
        System.out.println("origin str=" + originStr); // 这里看到的是数据库中select看到的值
        String jsonStr = jsonObject.toString();
        System.out.println("json object=" + jsonStr); // 可见，与origin str没有变化

        JSONObject parameter = jsonObject.getJSONObject("parameter");
        String lineDelimiter = parameter.getString("lineDelimiter");
        System.out.println("get result: " + lineDelimiter); // 这里会丢失反斜杠

        String escapeDelimiter = StringEscapeUtils.escapeJava(lineDelimiter);
        System.out.println("escape: " + escapeDelimiter); // 这里会把反斜杠添加回去

        String newDelimiter = escapeDelimiter.replace("\\\\", "\\");

        if (parameter.containsKey("lineDelimiter")) {
            String originValue = "\"lineDelimiter\":\"" + escapeDelimiter + "\"";
            String newValue = "\"lineDelimiter\":\"" + newDelimiter + "\"";
            String newParam = parameter.toString().replace(originValue, newValue);
            jsonObject.put("parameter", JSON.parseObject(newParam));
        } else {
            System.out.println("没有lineDelimiter");
        }

        System.out.println(jsonObject.toString());
        System.out.println("after:" + jsonObject.getJSONObject("parameter").getString("lineDelimiter"));
        System.out.println("after:" + StringEscapeUtils.escapeJava(jsonObject.getJSONObject("parameter").getString("lineDelimiter")));
    }

    @Test
    public void testreplace() {
        String a = "\\n";
        //System.out.println(a.replace("a", "b"));

    }

    @Test
    public void test5() {
        String a = "a\\nb";
        System.out.println(a);
        System.out.println(a.length());
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b);
        System.out.println(b.length());
        System.out.println(a.contains("\\\\"));
        System.out.println(b.contains("\\\\"));
    }

    @Test
    public void test6() {
        String a = "\\n";
        System.out.println(a); // \n
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b); // \\n

        //System.out.println(StringUtils.escapeQuote(a,"\\"));// \\n

        System.out.println(StringEscapeUtils.unescapeJava(b));
    }

    @Test
    public void test7() {
        // 从数据库中读取到一个String
        String originStr = "{"
                + "\"parameter\":{"
                + "\"lineDelimiter\": \"a\\\\nb\""
                + "}"
                + "}";
        // 在Java中转化为JSON Object
        JSONObject jsonObject = JSON.parseObject(originStr);
        System.out.println("origin str = " + originStr);// 输出\\n  这里看到的是数据库中select看到的值
        System.out.println("origin str length = " + originStr.length()); // 40
        System.out.println("json object str = " + jsonObject.toString());// 输出\\n  可见，与origin str没有变化
        System.out.println("json object str length = " + jsonObject.toString().length()); // 39

        JSONObject parameter = jsonObject.getJSONObject("parameter");
        String lineDelimiter = parameter.getString("lineDelimiter");
        System.out.println("get result: " + lineDelimiter); // 输出\n  这里会丢失反斜杠

        String unescapeDelimiter = StringEscapeUtils.unescapeJava(lineDelimiter);
        System.out.println("unescape str = " + unescapeDelimiter); // 输出 a换行b，即\n会被标准输出
        //String espaceDelimiter = StringEscapeUtils.escapeJava(lineDelimiter);
        //System.out.println("espace str = " + espaceDelimiter); // 输出a\\nb，即\\n会被原封不动地输出

        parameter.put("lineDelimiter", unescapeDelimiter); // 这里会修改JSON Object
        //parameter.put("lineDelimiter2", espaceDelimiter);

        // 再次打印JSON Object，即变更后的JSON Object
        System.out.println("json object str = " + jsonObject.toString()); // 输出\n，即这里会将\n正确写回JSON Object
        System.out.println("json object str length = " + jsonObject.toString().length()); // 38
    }

    @Test
    public void test8() {
        String a = String.format("abc:%s", "aa\\n\\\\b");
        System.out.println(a);
        System.out.println(StringEscapeUtils.escapeJava(a));
        Map<String, String> map = new HashMap<>();
        map.put(a, a);
        System.out.println(map);
    }
}

