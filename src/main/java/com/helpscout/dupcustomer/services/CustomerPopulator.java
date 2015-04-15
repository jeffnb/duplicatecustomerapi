package com.helpscout.dupcustomer.services;

import com.helpscout.dupcustomer.models.Customer;
import com.helpscout.dupcustomer.repositories.CustomerRepository;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

/**
 * This class uses 2 different data factories.  Unfortunately, fairy has a bug but was the best
 * library at generating sane data for names and emails.  Datafactory is just a nice number
 * generator.
 */
public class CustomerPopulator {
  @Autowired CustomerRepository customerRepository;

  private Fairy fairy;
  private DataFactory dataFactory;

  public CustomerPopulator(){
    fairy = Fairy.create();
    dataFactory = new DataFactory();
  }

  @PostConstruct
  public void populateDatabase(){
    List<Customer> fakeCustomers = createDuplicateCustomers();
    fakeCustomers.addAll(createFakeCustomers(10000));
    customerRepository.save(fakeCustomers);
  }

  /**
   * Creates an arbitrary amount of fake customers
   * @return list of customers
   */
  public List<Customer> createFakeCustomers(int amount){
    List<Customer> customers = new ArrayList<Customer>();

    for(int i = 0; i < amount; i++){
      Person p = fairy.person();

      customers.add(new Customer(p.firstName(), p.lastName(), generatePhoneNumber(),
                                 p.email()));
    }

    return customers;
  }

  public List<Customer> createDuplicateCustomers(){
    List<Customer> customers = new ArrayList<Customer>();

    customers.add(new Customer("Frodo", "Baggins", "123-456-7890", "fbaggins@bagend.com"));
    customers.add(new Customer("Bilbo", "Baggins", "123-456-7890", "bbaggins@bagend.com"));

    return customers;
  }

  /**
   * Due to a bug in the fairy library the phone numbers are malformed and thus have to generated
   * @return
   */
  public String generatePhoneNumber(){
    return new StringBuilder().append(dataFactory.getNumberBetween(100, 999)).append("-")
        .append(dataFactory.getNumberBetween(100, 999))
        .append("-").append(dataFactory.getNumberBetween(1000, 9999)).toString();
  }
}
