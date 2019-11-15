package com.lambdaschool.zoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.models.ZooAnimals;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZooControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredResponseTime() {
        given().when().get("/zoos/zoos")
                .then()
                .time(lessThan(5000L));
    }

    @Test
    public void givenFindAllZoos() {
        given().when().get("/zoos/zoos")
                .then()
                .statusCode(200)
                .and()
                .body(containsString("zoo"));
    }

    @Test
    public void givenFoundZooName() {
        String aZoo = "San Diego";

        given().when().get("/zoos/zoo/namelike/" + aZoo)
                .then()
                .statusCode(200)
                .and()
                .body(containsString("Zoo"));
    }

    @Test
    public void givenFoundZooId() {
        long aZoo = 1L;

        given().when().get("/zoos/zoo/" + aZoo)
                .then()
                .statusCode(200)
                .and()
                .body(containsString("Zoo"));
    }

    @Test
    public void givenPostAZoo() throws Exception {
        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z6 = new Zoo("San Francisco Zoo Test", animals);
        ObjectMapper mapper = new ObjectMapper();
        String stringZ6 = mapper.writeValueAsString(z6);

        given().contentType("application/json")
                .body(stringZ6)
                .when()
                .post("/zoos/zoo")
                .then()
                .statusCode(201);
    }

    @Test
    public void givenUpdateAZoo() throws Exception {
        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z1 = new Zoo("Glady's Porter Zoo Test", animals);
        ObjectMapper mapper = new ObjectMapper();
        String stringZ1 = mapper.writeValueAsString(z1);

        given().contentType("application/json")
                .body(stringZ1)
                .when()
                .put("zoos/zoo/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void givenDeleteAZoo() {
        long aZoo = 1L;
        given().when()
                .delete("zoos/zoo/" + aZoo)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenPostAnAnimal() {
        given().when()
                .post("/zoos/zoo/1/animals/3")
                .then()
                .statusCode(201);
    }

    @Test
    public void givenDeleteAnAnimal() {
        given().when()
                .delete("zoos/zoo/1/animals/2")
                .then()
                .statusCode(200);
    }
}
