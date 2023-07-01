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
import java.util.stream.Collectors;

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
}
