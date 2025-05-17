package Utility;

import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Base extends CommonFunctions {

    public static Response response;
    public static JSONObject body;
    public static  String baseUrl;

    public static Response sendPost(String url, String key1, String value1, String key2, String value2) {
        body = new JSONObject();
        body.put(key1, value1);
        body.put(key2, value2);

        return RestAssured.given()
                .header("x-api-key", getProperty("apiKey"))
                .contentType(ContentType.JSON)
                .body(body.toJSONString())
                .when()
                .post(url);
    }

    public static Response sendGet(String url) {
        return RestAssured.given()
                .header("x-api-key", getProperty("apiKey"))
                .when()
                .get(url);
    }
    
    public static Response sendPut(String url, String key1, String value1, String key2, String value2) {
        body = new JSONObject();
        body.put(key1, value1);
        body.put(key2, value2);

        return RestAssured.given()
                .header("x-api-key", getProperty("apiKey"))
                .contentType(ContentType.JSON)
                .body(body.toJSONString())
                .when()
                .put(url);
    }

    public static Response sendDelete(String url) {
        return RestAssured.given()
                .header("x-api-key", getProperty("apiKey"))
                .when()
                .delete(url);
    }
    public static String getrequest(String url, int expectedStatusCode) {
        response = sendGet(url);
        int actualStatusCode = response.getStatusCode();
        System.out.println("GET Status Code: " + actualStatusCode);
        validateStatusCode(expectedStatusCode, actualStatusCode);
        return response.getBody().asString();
    }

    public static String deleterequest(String url, int expectedStatusCode) {
        response = sendDelete(url);
        int actualStatusCode = response.getStatusCode();
        System.out.println("DELETE Status Code: " + actualStatusCode);
        validateStatusCode(expectedStatusCode, actualStatusCode);
        return response.getBody().asString();
    }

    public static String Postrequest(String url, String key1, String value1, String key2, String value2, int expectedStatusCode, long expectedTime) {
        response = sendPost(url, key1, value1, key2, value2);
        validateStatusCode(expectedStatusCode, response.getStatusCode());
        validateResponseTime(expectedTime, response.getTime());
        return response.getBody().asString();
    }

    public static String Putrequest(String url, String key1, String value1, String key2, String value2, int expectedStatusCode, long expectedTime) {
        response = sendPut(url, key1, value1, key2, value2);
        validateStatusCode(expectedStatusCode, response.getStatusCode());
        validateResponseTime(expectedTime, response.getTime());
        return response.getBody().asString();
    }

    public static void validateStatusCode(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected status: " + expected + " but got: " + actual);
        }
    }

    public static void validateResponseTime(long expectedTime, long actualTime) {
        if (expectedTime > 0 && actualTime > expectedTime) {
            throw new AssertionError("Response time exceeded: " + actualTime + "ms (expected <= " + expectedTime + "ms)");
        }
    }
}
