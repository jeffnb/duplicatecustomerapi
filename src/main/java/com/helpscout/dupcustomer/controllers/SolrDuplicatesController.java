package com.helpscout.dupcustomer.controllers;

import com.helpscout.dupcustomer.services.SolrHelper;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Duplicates controller for solr based duplicates
 */
@RestController
@RequestMapping("solr-duplicates/")
public class SolrDuplicatesController {

  @Autowired private SolrHelper solrHelper;

  /**
   * Simple duplicate email finder.  Email addresses must be exact.
   * @return
   */
  @RequestMapping(value = "email", method = RequestMethod.GET)
  public Map<String, SolrDocumentList> duplicateEmail() throws SolrServerException {
    return getDuplicatesByField("email");
  }

  /**
   * Simple duplicate email finder.  Email addresses must be exact.
   * @return
   */
  @RequestMapping(value = "phone", method = RequestMethod.GET)
  public Map<String, SolrDocumentList> duplicatePhone() throws SolrServerException {
    return getDuplicatesByField("phone");
  }

  /**
   * This method takes the field and does a facet on it with a minimum count. Then for each limiting
   * facet it will call out to the solr server to get the documents matching and return. This is the
   * only spot in the code that uses solr specific classes outside of the SolrHelper class.  This
   * is for simplicity sake in formatting.
   * @param fieldName
   * @return
   * @throws SolrServerException
   */
  public Map<String, SolrDocumentList> getDuplicatesByField(String fieldName)
      throws SolrServerException {
    Map<String, SolrDocumentList> dupMap = new HashMap<String, SolrDocumentList>();

    FacetField field = solrHelper.getDuplicateValuesByField(fieldName);

    //First get each duplicate email
    for(FacetField.Count c : field.getValues()){
      SolrDocumentList documents = solrHelper.getDocumentsByFilterQuery(c.getAsFilterQuery());

      if(documents != null && documents.size() > 0) {
        dupMap.put(c.getName(), documents);
      }
    }

    return dupMap;
  }
}
