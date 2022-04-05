package com.automationpractice.test;

import com.automationpractice.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    public static void setUp() {

        baseURI = ConfigurationReader.getProperty("automationpractice.api.url");

    }
}
