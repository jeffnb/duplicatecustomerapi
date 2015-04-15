package com.helpscout.dupcustomer.controllers;

import com.helpscout.dupcustomer.DupCustomerApp;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertTrue;

/**
 * Tests the duplicate controller endpoints
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DupCustomerApp.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0"})
public class DuplicateControllerITCase {
  @Value("${local.server.port}")
  private int serverPort;

  @Before
  public void setUp(){
    RestAssured.port = serverPort;
  }

  @Test
  public void duplicatePhone_ExpectTestDuplicate(){
    when()
        .get("/duplicates/phone")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("123-456-7890.id", hasItems(1, 2));
  }

  /**
   * Due to emails containing dots we pull the map back manually to test
   */
  @Test
  public void duplicateEmail_ExpectTestDuplicate(){
    String json = get("/duplicates/email").asString();
    JsonPath jsonPath = new JsonPath(json);
    Map<String, List> items = jsonPath.getMap("");

    assertTrue("Test email duplicate missing", items.containsKey("baggins@bagend.com"));
  }

  @Test
  public void duplicateNamePhone_ExpectTestDuplicate(){
    when()
        .get("/duplicates/last-name-phone")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("Baggins 123-456-7890.id", hasItems(1, 2));
  }
}
