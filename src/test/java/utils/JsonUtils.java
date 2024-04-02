package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    public static boolean validateJsonSchema(Response response, String filePath) {
        String schemaString = JsonUtils.convertObjectNodeToJsonString(JsonUtils.readJsonSchema(filePath));
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaString));
            return true;
        } catch (AssertionError assertionError) {
            return false;
        }

    }

    private static ObjectNode readJsonSchema(String schemaFilePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (ObjectNode) mapper.readTree(new File(schemaFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertObjectNodeToJsonString(ObjectNode objectNode) {
        try {
            return new ObjectMapper().writeValueAsString(objectNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
