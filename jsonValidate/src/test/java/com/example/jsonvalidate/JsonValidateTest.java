package com.example.jsonvalidate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author 匠承
 * @Date: 2023/5/25 09:43
 */
public class JsonValidateTest {



    @Test
    public void test() throws IOException {
         //从Json文件中读取Json Schema
        URL schemaUrl = JsonValidateTest.class.getResource("/schema.json");
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(JsonValidateTest.class.getResourceAsStream("/schema.json"));

        // 将要验证的Json数据转换为JsonNode对象
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = this.getClass().getResourceAsStream("/file.json");
        JsonNode dataNode = objectMapper.readTree(inputStream);

        // 验证Json数据是否符合Json Schema
        Set<ValidationMessage> set = jsonSchema.validate(dataNode);
        for(ValidationMessage validationMessage : set){
            System.out.println(validationMessage);
        }
    }

    @Test
    public void test2(){
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
}