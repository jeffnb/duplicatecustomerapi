package com.helpscout.dupcustomer.services;

import com.helpscout.dupcustomer.models.Customer;
import com.helpscout.dupcustomer.repositories.CustomerRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jeff on 4/13/15.
 */
public class CustomerPopulatorTest {
  @Mock CustomerRepository customerRepository;

  @InjectMocks CustomerPopulator customerPopulator;

  @Before
  public void initMocks(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void generatePhoneNumber_GenerateCorrectLengthAndFormat(){
    Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");

    //Loop through many times to get reasonable certainty that method works
    for(int i = 0; i < 100; i++){
      String phone = customerPopulator.generatePhoneNumber();
      Matcher matcher = pattern.matcher(phone);

      assertTrue("Phone number invalid", matcher.matches());
    }
  }

  @Test
  public void createFakeCustomers_AskFor100_Generates100Customers(){
    List<Customer> customers = customerPopulator.createFakeCustomers(100);
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    for(Customer c : customers){
      Set constraintsViolations = validator.validate(c);
      assertEquals("Validation failed", 0, constraintsViolations.size());
    }

    assertEquals("Incorrect customer count", 100, customers.size());
  }

}
