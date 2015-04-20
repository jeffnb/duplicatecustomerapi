package com.helpscout.dupcustomer.repositories;

import com.helpscout.dupcustomer.models.Customer;
import com.helpscout.dupcustomer.services.SolrHelper;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * This class listens to entities to be saved into the database then sends them to the solr
 * instance as well to keep them in sync.
 *
 * The static variable is a strange hack that was required since the CustomerEntityListener is
 * instantiated by the Entity and Spring data.  So this way provides a path to injecting the
 * SolrHelper bean and keeping the code concise. 
 */
@Component
public class CustomerEntityListener {

  static private SolrHelper solrHelper;

  @Autowired(required = true)
  public void setSolrHelper(SolrHelper solrHelper){
    CustomerEntityListener.solrHelper = solrHelper;
  }

  @PostRemove
  public void postRemove(Customer customer) throws IOException, SolrServerException {
    solrHelper.deleteByIdWithCommit(String.valueOf(customer.getId()));
  }

  @PostPersist
  public void postPersist(Customer customer){
    solrHelper.saveUpdateCustomer(customer);
  }

  @PostUpdate
  public void postUpdate(Customer customer){
    solrHelper.saveUpdateCustomer(customer);
  }
}
