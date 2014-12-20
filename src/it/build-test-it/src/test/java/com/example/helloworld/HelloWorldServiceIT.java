package com.example.helloworld;


import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class HelloWorldServiceIT {

    @Test
    public void test() throws Exception {
        given().
                log().all().
                expect().
                statusCode(200).
                body(containsString("Hello, Stranger!")).
                when().
                get("http://localhost:8080/hello-world");
    }
}
