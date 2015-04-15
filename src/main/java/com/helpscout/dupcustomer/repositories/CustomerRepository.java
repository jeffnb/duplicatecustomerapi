package com.helpscout.dupcustomer.repositories;

import com.helpscout.dupcustomer.models.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Main point of entry into db.  Uses spring data aka hibernate
 */
@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  List<Customer> findByEmail(@Param("email") String email);
  List<Customer> findByPhone(@Param("phone") String phone);

  List<Customer> findByPhoneAndLastName(@Param("phone") String phone,
                                        @Param("lastName") String lastName);

  @Query("SELECT email FROM Customer GROUP BY email HAVING count(email) > 1")
  List<String> findDuplicateEmail();

  @Query("SELECT phone FROM Customer GROUP BY phone HAVING count(phone) > 1")
  List<String> findDuplicatePhones();

  @Query("SELECT new com.helpscout.dupcustomer.models.Customer(lastName, phone) "
         + "FROM Customer c GROUP BY lastName, phone Having count(*) > 1")
  List<Customer> findSimilarCustomers();

}
