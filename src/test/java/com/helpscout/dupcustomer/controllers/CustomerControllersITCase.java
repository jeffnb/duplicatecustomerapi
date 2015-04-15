package com.helpscout.dupcustomer.controllers;

import com.helpscout.dupcustomer.DupCustomerApp;
import com.helpscout.dupcustomer.repositories.CustomerRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.Is.is;

/**
 * Tests the built in functions of the spring data system
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DupCustomerApp.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0"})
public class CustomerControllersITCase {

  @Value("${local.server.port}")
  private int serverPort;

  @Autowired CustomerRepository customerRepository;

  @Before
  public void setUp(){
    RestAssured.port = serverPort;
  }

  @Test
  public void getOne_ExpectRecordReturn(){
    when()
        .get("/customers/1")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("firstName", is("Frodo"));

  }

  @Test
  public void getFirstPage_ExpectCorrectElementsAndLink(){
    when()
        .get("/customers")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("_links.next.href", anything());
  }

  /**
   * Tests to ensure the object was created
   */
  @Test
  public void postNewCustomer_ExpectReturnedObject(){
    String newCustomer = "{  \"firstName\" : \"Gandalf\",  \"lastName\" : \"The Grey\", \"phone\" : 1235551111, \"email\": \"gandalf@wizards.gov\" }";
    given()
        .contentType(ContentType.JSON)
        .body(newCustomer)
        .when().post("/customers")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .body("_links.self.href", isA(String.class));
  }

  @Test
  public void deleteCustomer_Expect404(){

    //Make sure we get a record
    when()
        .get("/customers/3")
        .then()
        .statusCode(HttpStatus.SC_OK);

    //Delete it
    when()
        .delete("/customers/3")
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);

    //Make sure not there
    //Make sure we get a record
    when()
        .get("/customers/3")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
