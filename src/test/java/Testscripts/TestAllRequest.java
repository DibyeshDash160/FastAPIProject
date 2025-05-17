package Testscripts;

import Utility.Base;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;

@Epic("User API Tests")
@Feature("CRUD operations for users")
public class TestAllRequest extends Base {

public static  String userId; 
public static int GetExpectedStatusCode;
public static int InvalidStatuscode;
public static long Responsetime;

    
 // ------------------ Positive Test Cases ------------------ //
    
    @Test(description = "Create user with valid data",priority = 1)
    public static void TC01_CreateUser_POST() throws IOException {
        baseUrl = EnvConfigManager("baseUrl")+getProperty("PostRequestPathParameter");
        String NameValue = getProperty("PostRequestNameValue");
        String JobValue = getProperty("PostRequestJobValue");
        int PostRequestExpectedStatusCode = Integer.parseInt(getProperty("PostRequestExpectedStatusCode"));
        response = Base.sendPost(baseUrl, "name", NameValue, "job", JobValue);
        Assert.assertEquals(response.getStatusCode(), PostRequestExpectedStatusCode);
        String PostRequestUserId = response.jsonPath().getString("id");
        Assert.assertNotNull(PostRequestUserId, "User ID is null");
        response.then().assertThat().body(matchesJsonSchemaInClasspath("createUserSchema.json"));
    }
    
    @Test(priority = 2,dependsOnMethods = "TC01_CreateUser_POST")
    public static void TC02_GetListOfUser() {
        String getUrl = baseUrl + getProperty("GetRequestUrl");
        response = Base.sendGet(getUrl);
        GetExpectedStatusCode=Integer.parseInt(getProperty("GetExpectedStatusCode"));
        Assert.assertEquals(response.getStatusCode(), GetExpectedStatusCode);
        userId = response.jsonPath().getString("data[0].id");
//        response.then().assertThat().body(matchesJsonSchemaInClasspath("createUserSchema.json"));
    }

    @Test(priority = 3, dependsOnMethods = "TC02_GetListOfUser")
    public static void TC03_GetUserById() {
        String getUrl = baseUrl + "/" + userId;
        response = Base.sendGet(getUrl);
        System.out.println("GET Response: " + response.asString());
        GetExpectedStatusCode=Integer.parseInt(getProperty("GetExpectedStatusCode"));
        Assert.assertEquals(response.getStatusCode(), GetExpectedStatusCode);
        String actualId = response.jsonPath().getString("data.id");
//        System.out.println("Actual userId :"+actualId);
        Assert.assertEquals(actualId, userId, "Returned ID does not match created user ID");
//        response.then().assertThat().body(matchesJsonSchemaInClasspath("createUserSchema.json"));
    }

    @Test(priority = 4, dependsOnMethods = "TC03_GetUserById")
    public static void TC04_UpdateUser_PUT() {
        String putUrl = baseUrl + getProperty("PUTRequestUrl") + userId;
        String UpdateNameValue = getProperty("PostRequestNameValue");
        String UpdateJobValue = getProperty("PostRequestJobValue");
        response = Base.sendPut(putUrl, "name", UpdateNameValue, "job", UpdateJobValue);
        System.out.println("PUT Response: " + response.asString());
        int PUTExpectedStatusCode=Integer.parseInt(getProperty("PUTExpectedStatusCode"));
        Assert.assertEquals(response.getStatusCode(), PUTExpectedStatusCode);
        String name = response.jsonPath().getString("name");
        String position = response.jsonPath().getString("job");
        System.out.println(name+position);
        Assert.assertTrue(name.contains(UpdateNameValue), "Name does not match expected value");
        Assert.assertTrue(position.contains(UpdateJobValue), "Name does not match expected value");
    }

    @Test(priority = 5, dependsOnMethods = "TC04_UpdateUser_PUT")
    public static void TC05_DeleteUser_DELETE() {
        String deleteUrl = baseUrl + getProperty("DeleteRequestUrl")+ userId;
        response = Base.sendDelete(deleteUrl);
        int DeleteExpectedStatusCode=Integer.parseInt(getProperty("DeleteExpectedStatusCode"));
        Assert.assertEquals(response.getStatusCode(), DeleteExpectedStatusCode);
    }

    // ------------------ Negative Test Cases ------------------ //

    @Test(priority = 6)
    public static void TC06_PostRequest_MissingFields() {
    	String BlankNameValue = getProperty("BlankNamevalue");
        String BlankJobValue = getProperty("BlankJobValue");
        baseUrl = getProperty("BaseUrl");
        InvalidStatuscode=Integer.parseInt(getProperty("InvalidStatuscode"));
        Responsetime=Long.parseLong(getProperty("Responsetime"));
        Base.Postrequest(baseUrl, "name", BlankNameValue, "job", BlankJobValue, InvalidStatuscode, Responsetime);
    }

    @Test(priority = 7)
    public static void TC07_GetRequest_InvalidEndpoint() {
        String invalidUrl = getProperty("BaseUrl") + getProperty("InvalidId");
        InvalidStatuscode=Integer.parseInt(getProperty("InvalidStatuscode"));
        Base.getrequest(invalidUrl, InvalidStatuscode);
    }

    @Test(priority = 8)
    public static void TC08_PutRequest_MissingName() {
    	String BlankNameValue = getProperty("BlankNamevalue");
        String InvalidJobValue = getProperty("InvalidJobValue");
        baseUrl = getProperty("BaseUrl");
        InvalidStatuscode=Integer.parseInt(getProperty("InvalidStatuscode"));
        Responsetime=Long.parseLong(getProperty("Responsetime"));
       Base.Putrequest(baseUrl, "name", BlankNameValue, "job", InvalidJobValue, InvalidStatuscode, Responsetime);
    }

    @Test(priority = 9)
    public static void TC09_DeleteRequest_InvalidEndpoint() {
        String invalidDeleteUrl = getProperty("BaseUrl") + getProperty("InvalidId");
        InvalidStatuscode=Integer.parseInt(getProperty("InvalidStatuscode"));
        Base.deleterequest(invalidDeleteUrl, InvalidStatuscode);
    }
}
