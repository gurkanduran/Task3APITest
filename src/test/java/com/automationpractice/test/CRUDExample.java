package com.automationpractice.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CRUDExample extends TestBase{

    @Test
    public void createNewUser(){
        String requestJson = "{\"name\": \"Alan\",\n" +
                "\"age\": 30,\n" +
                "\"country\": \"USA\"}";
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestJson)
                .when().post("/api/user");

        System.out.println("status code = " + response.statusCode());
        response.prettyPrint();

        assertThat(response.statusCode(), is(201));
        assertEquals(201, response.statusCode());

        assertThat(response.contentType(), is("application/json"));


    }

    @Test
    public void updateUser() {
        Map<String, Object> userMap = new LinkedHashMap<>();
        userMap.put("name", "Alan");
        userMap.put("age", 34);
        userMap.put("country", "USA");

        given().contentType(ContentType.JSON)
                .and().body(userMap)
                .when().put("/api/user/1")
                .then().assertThat().statusCode(204);

    }

    @Test
    public void getUser(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .when().get("/api/user/{id}");

        System.out.println("status code = " + response.statusCode());
        assertEquals(200, response.statusCode());

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals("application/json", response.contentType());

        response.prettyPrint();
        assertTrue(response.asString().contains("Alan"));

    }

    @Test
    public void getNegativeTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 20)
                .when().get("/api/user/{id}");

        System.out.println("status code = " + response.statusLine());
        assertEquals(404, response.statusCode());

        System.out.println("content type = " + response.contentType());
        assertEquals("application/json", response.contentType());

        response.prettyPrint();
        assertTrue(response.asString().contains("Not Found"));
    }
}
