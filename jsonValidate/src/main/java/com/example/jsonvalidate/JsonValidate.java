package com.example.jsonvalidate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 匠承
 * @Date: 2023/5/25 09:34
 */
public class JsonValidate {

    private static final ObjectMapper mapper = new ObjectMapper();

    static final String json1 = "{\"a\":1}";
    static final String json1_schema = "{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"type\":\"object\",\"properties\":{\"a\":{\"type\":\"number\"}}}";

    //public static void main(String[] args) {
    //    try {
    //        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator()
    //                .validateUnchecked(mapper.readTree(json1_schema),mapper.readTree(json1));
    //        if (report.isSuccess()) {
    //            System.out.println("valid success...");
    //        } else {
    //            List<JsonNode> errorsJsonArray = new ArrayList<>();
    //            Iterator<ProcessingMessage> iterator = report.iterator();
    //            while (iterator.hasNext()) {
    //                errorsJsonArray.add(iterator.next().asJson());
    //            }
    //            System.out.println(errorsJsonArray.toString());
    //        }
    //    }  catch (JsonProcessingException e) {//jackson的异常
    //        e.printStackTrace();
    //    }
    //}

    //public static void main(String[] args) throws IOException, ProcessingException {
    //    // 从Json文件中读取Json Schema
    //    URL schemaUrl = JsonValidate.class.getResource("/schema.json");
    //    JsonNode schemaNode = JsonLoader.fromURL(schemaUrl);
    //    JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
    //    JsonSchema schema = factory.getJsonSchema(schemaNode);
    //
    //    // 将要验证的Json数据转换为JsonNode对象
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    JsonNode dataNode = objectMapper.readTree("{\"name\":\"Tom\",\"age\":20}");
    //
    //    // 验证Json数据是否符合Json Schema
    //    boolean isValid = schema.validate(dataNode).isSuccess();
    //    System.out.println(isValid);
    //}
}
