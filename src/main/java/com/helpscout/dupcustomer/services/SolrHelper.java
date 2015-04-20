package com.helpscout.dupcustomer.services;

import com.helpscout.dupcustomer.models.Customer;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Simple wrapper to keep all solr code in one class across the system and ensure commits are sent
 */
public class SolrHelper {

  private SolrServer solrServer;

  public SolrHelper(String solrUrl){
    this.solrServer = new HttpSolrServer(solrUrl);
  }

  /**
   * Simple wrapper for deleting with a commit
   */
  public void deleteIndex(){
    try {
      solrServer.deleteByQuery("*:*");
      solrServer.commit();
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes with a specified id and also commits
   * @param id
   */
  public void deleteByIdWithCommit(String id){
    try {
      solrServer.deleteById(id);
      solrServer.commit();
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Simply converts the customer to a solrdocument before sending and committing
   * @param customer
   */
  public void saveUpdateCustomer(Customer customer){
    SolrInputDocument doc = new SolrInputDocument();
    doc.addField("id", customer.getId());
    doc.addField("firstName", customer.getFirstName());
    doc.addField("lastName", customer.getLastName());
    doc.addField("email", customer.getEmail());
    doc.addField("phone", customer.getPhone());

    try {
      solrServer.add(doc);
      solrServer.commit();
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Connects to solr and gets any duplicate data based on the address
   * @param fieldName
   * @return
   */
  public FacetField getDuplicateValuesByField(String fieldName){
    FacetField field = null;

    //Get all email facets with duplicates
    SolrQuery query = new SolrQuery("*:*");
    query.addFacetField(fieldName);
    query.setFacetMinCount(2);
    query.setRows(0);
    QueryResponse response = null;
    try {
      response = solrServer.query(query);
      field = response.getFacetField(fieldName);
    } catch (SolrServerException e) {
      e.printStackTrace();
    }

    return field;
  }

  /**
   * Applies the filter for a field and returns the solr documents.
   * @param filterQuery
   * @return
   */
  public SolrDocumentList getDocumentsByFilterQuery(String filterQuery){
    SolrDocumentList solrDocuments = null;
    SolrQuery dupQuery = new SolrQuery("*:*");
    dupQuery.addFilterQuery(filterQuery);
    dupQuery.setRows(100);
    try {
      QueryResponse docResponse = solrServer.query(dupQuery);
      solrDocuments = docResponse.getResults();
    } catch (SolrServerException e) {
      e.printStackTrace();
    }

    return solrDocuments;
  }

}
