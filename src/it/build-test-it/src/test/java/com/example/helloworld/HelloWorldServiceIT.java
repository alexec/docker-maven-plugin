package com.example.helloworld;


import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class HelloWorldServiceIT {

    @Test
    public void test() throws Exception {
	String host = System.getProperty("example.app.ip");

        Thread.sleep(5000); // since I removed healthcheck, the service is not immediately available when the test is started
        //need some rework on the healthcheck part, so that the healthcheck could also use the ip address of container
        given().
                log().all().
                expect().
                statusCode(200).
                body(containsString("Hello, Stranger!")).
                when().
                get("http://"+ host + ":8080/hello-world");
    }
}
