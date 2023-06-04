package com.example.jsonvalidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/5/25 09:43
 */
public class JsonValidateTest {

    static JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

    @Test
    public void test() throws IOException {
        //从Json文件中读取Json Schema
        //URL schemaUrl = JsonValidateTest.class.getResource("/schema.json");
        JsonSchema jsonSchema = factory.getSchema(JsonValidateTest.class.getResourceAsStream("/schema.json"));

        // 将要验证的Json数据转换为JsonNode对象
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("abc");
        System.out.println(System.getProperty("java.class.path"));
        File file = new File("/Users/aldrichzeng/IdeaProjects/Learning2023/Learning2023/jsonValidate/src/main/resources/file.json");
        InputStream inputStream = new FileInputStream(file);
        //InputStream inputStream = this.getClass().getResourceAsStream("");
        JsonNode dataNode = objectMapper.readTree(inputStream);

        // 验证Json数据是否符合Json Schema
        Set<ValidationMessage> set = jsonSchema.validate(dataNode);
        System.out.println(set.stream().map(e->e.getMessage()).collect(Collectors.joining(",")));
        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void test4() throws IOException {

        // 将要验证的Json数据转换为JsonNode对象
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = this.getClass().getResourceAsStream("/file.json");
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

        String jsonString = scanner.hasNext() ? scanner.next() : "";
        JsonNode dataNode = objectMapper.readTree(jsonString);

        // 验证Json数据是否符合Json Schema
        JsonSchema jsonSchema = factory.getSchema(JsonValidateTest.class.getResourceAsStream("/schema.json"));
        Set<ValidationMessage> set = jsonSchema.validate(dataNode);
        for (ValidationMessage validationMessage : set) {
            System.out.println(validationMessage);
        }
    }

    String jsonSchemaPattern = "{\n"
            + "  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n"
            + "  \"type\": \"object\",\n"
            + "  \"properties\": {\n"
            + "    \"name\": {\n"
            + "      \"type\": \"string\"\n"
            + "    },\n"
            + "    \"age\": {\n"
            + "      \"type\": \"integer\"\n"
            + "    },\n"
            + "    \"hello\": {\n"
            + "      \"type\": \"integer\"\n"
            + "    }\n"
            + "  },\n"
            + "  \"required\": [\"name\", \"age\"]\n"
            + "}\n";

    @Test
    public void test3() throws JsonProcessingException {
        //从Json文件中读取Json Schema
        //URL schemaUrl = JsonValidateTest.class.getResource("/schema.json");
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(jsonSchemaPattern);

        // 将要验证的Json数据转换为JsonNode对象
        String input = "{\n"
                + "  \"name\": \"Tom\",\n"
                + "  \"age\": 20,\n"
                + "  \"hello\": \"abc\",\n"
                + "  \"a\": 1\n"
                + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataNode = objectMapper.readTree(input);

        // 验证Json数据是否符合Json Schema
        Set<ValidationMessage> set = jsonSchema.validate(dataNode);
        //for(ValidationMessage validationMessage : set){
        //    System.out.println(validationMessage);
        //}
        //System.out.println(set.isEmpty());
        System.out.println(set.stream().map(e -> e.getMessage()));
    }

    @Test
    public void test2() {
        //JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
        //JsonNode node = getJsonNodeFromStringContent("7");
        //Set<ValidationMessage> errors = schema.validate(node);
        //Assert.assertThat(errors.size(), is(1));
        //
        //// With automatic version detection
        //JsonNode schemaNode = getJsonNodeFromStringContent(
        //        "{\"$schema\": \"http://json-schema.org/draft-06/schema#\", \"properties\": { \"id\": {\"type\": \"number\"}}}");
        //JsonSchema schema = getJsonSchemaFromJsonNodeAutomaticVersion(schemaNode);
        //
        //schema.initializeValidators(); // by default all schemas are loaded lazily. You can load them eagerly via
        //// initializeValidators()
        //
        //JsonNode node = getJsonNodeFromStringContent("{\"id\": \"2\"}");
        //Set<ValidationMessage> errors = schema.validate(node);
        //assertThat(errors.size(), is(1));
    }

    @Test
    public void test5(){
    }
}