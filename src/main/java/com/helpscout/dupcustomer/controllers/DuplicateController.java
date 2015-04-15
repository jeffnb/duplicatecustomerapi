package com.helpscout.dupcustomer.controllers;

import com.helpscout.dupcustomer.models.Customer;
import com.helpscout.dupcustomer.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeff on 4/14/15.
 */
@RestController
@RequestMapping("duplicates/")
public class DuplicateController {

  @Autowired CustomerRepository repository;

  /**
   * Simple duplicate email finder.  Email addresses must be exact.
   * @return
   */
  @RequestMapping(value = "email", method = RequestMethod.GET)
  public Map<String, List<Customer>> duplicateEmail(){
    Map<String, List<Customer>> dupMap = new HashMap<String, List<Customer>>();

    for(String email : repository.findDuplicateEmail()){
      dupMap.put(email, repository.findByEmail(email));
    }

    return dupMap;
  }

  /**
   * Duplicate phone number finder.  This is less useful for businesses with multiple users
   * and same phone number
   * @return
   */
  @RequestMapping(value = "phone", method = RequestMethod.GET)
  public Map<String, List<Customer>> duplicatePhone(){
    Map<String, List<Customer>> dupMap = new HashMap<String, List<Customer>>();

    for(String phone : repository.findDuplicatePhones()){
      dupMap.put(phone, repository.findByPhone(phone));
    }

    return dupMap;
  }

  @RequestMapping(value = "last-name-phone", method = RequestMethod.GET)
  public Map<String, List<Customer>> duplicateNamePhone(){
    Map<String, List<Customer>> dupMap = new HashMap<String, List<Customer>>();

    for(Customer partial : repository.findSimilarCustomers()){
      dupMap.put(partial.getLastName() + " " + partial.getPhone(),
                 repository.findByPhoneAndLastName(partial.getPhone(), partial.getLastName()));
    }

    return dupMap;
  }
}
