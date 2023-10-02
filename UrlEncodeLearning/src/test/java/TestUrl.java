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

import com.mysql.jdbc.StringUtils;
import javafx.beans.binding.StringExpression;
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
        String str = "{"
                + "\"parameter\":{"
                + "\"lineDelimiter\": \"a\\\\nb\""
                + "}"
                + "}";
        JSONObject object = JSON.parseObject(str);
        System.out.println("str=" + str);
        String objectStr = object.toString();
        System.out.println("object=" + object.toString());
        JSONObject parameter = object.getJSONObject("parameter");
        String espaceDelimiter = parameter.getString("lineDelimiter");
        System.out.println("get result: " + espaceDelimiter);

        String originDelimiter = StringEscapeUtils.escapeJava(espaceDelimiter);
        System.out.println("escape: " + originDelimiter);

        String newDelimiter = originDelimiter.replace("\\\\", "\\");

        if (parameter.containsKey("lineDelimiter")) {
            String originValue = "\"lineDelimiter\":\"" + originDelimiter + "\"";
            String newValue = "\"lineDelimiter\":\"" + newDelimiter + "\"";
            String newParam = parameter.toString().replace(originValue, newValue);
            object.put("parameter", JSON.parseObject(newParam));
        } else {
            System.out.println("没有lineDelimiter");
        }

        System.out.println(object.toString());
        System.out.println("after:" + object.getJSONObject("parameter").getString("lineDelimiter"));
        System.out.println("after:" + StringEscapeUtils.escapeJava(object.getJSONObject("parameter").getString("lineDelimiter")));
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
    public void test6(){
        String a = "\\n";
        System.out.println(a); // \n
        String b = StringEscapeUtils.escapeJava(a);
        System.out.println(b); // \\n

        //System.out.println(StringUtils.escapeQuote(a,"\\"));// \\n

        System.out.println(StringEscapeUtils.unescapeJava(b));

    }

    @Test
    public void test7(){
        String str = "{"
                + "\"parameter\":{"
                + "\"lineDelimiter\": \"a\\\\nb\""
                + "}"
                + "}";
        JSONObject object = JSON.parseObject(str);
        System.out.println("str=" + str);
        System.out.println(str.length());
        String objectStr = object.toString();
        System.out.println("object=" + object.toString());// \\n
        System.out.println(object.toString().length());
        JSONObject parameter = object.getJSONObject("parameter");
        String espaceDelimiter = parameter.getString("lineDelimiter");// \n
        System.out.println("get result: " + espaceDelimiter); // \n

        String newLineDelimiter = StringEscapeUtils.unescapeJava(espaceDelimiter);
        //String originDelimiter = StringEscapeUtils.escapeJava();

        parameter.put("lineDelimiter", newLineDelimiter);
        //parameter.put("lineDelimiter2",newLineDelimiter);
        System.out.println(object.toString());
        System.out.println(object.toString().length());


    }
    @Test
    public void test8(){
        String a= String.format("abc:%s", "aa\\n\\\\b");
        System.out.println(a);
        System.out.println(StringEscapeUtils.escapeJava(a));
        Map<String, String> map = new HashMap<>();
        map.put(a,a);
        System.out.println(map);
    }
}

