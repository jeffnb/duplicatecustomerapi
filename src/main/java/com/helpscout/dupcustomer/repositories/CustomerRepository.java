package com.helpscout.dupcustomer.repositories;

import com.helpscout.dupcustomer.models.Customer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jeff on 4/13/15.
 */
@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
