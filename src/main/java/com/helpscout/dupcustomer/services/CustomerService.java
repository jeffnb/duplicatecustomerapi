package com.helpscout.dupcustomer.services;

import com.helpscout.dupcustomer.models.Customer;
import com.helpscout.dupcustomer.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

/**
 * Created by jeff on 4/13/15.
 */
public class CustomerService {

  @Autowired CustomerRepository customerRepository;

  /**
   * The main purpose of this is to populate records in the database. Can be removed from
   * bean declaration
   */
  public void populateDatabase(){
    Fairy fairy = Fairy.create();
    List<Customer> customers = new ArrayList<Customer>();

    for(int i = 0; i < 100; i++){
      Person p = fairy.person();

      customers.add(new Customer(p.firstName(), p.lastName(), p.telephoneNumber(), p.email()));
    }

    customerRepository.save(customers);
  }

}
