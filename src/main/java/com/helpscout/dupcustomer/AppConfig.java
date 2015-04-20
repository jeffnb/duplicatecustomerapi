package com.helpscout.dupcustomer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.helpscout.dupcustomer.services.CustomerPopulator;
import com.helpscout.dupcustomer.services.SolrHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES;

/**
 * Configuration for the object mapper rest controllers are using as well as solr server connection
 */
@Configuration
public class AppConfig {

  @Value("${solr.url}") String solrUrl;

  /**
   * This method not only starts up the solr helper it will also request to clean the index. This is
   * needed since the database is currently in memory and will keep the db and solr in sync
   * @return
   */
  @Bean
  public SolrHelper solrHelper(){
    SolrHelper solrHelper = new SolrHelper(solrUrl);

    //Ask solrhelper to wipe the index
    solrHelper.deleteIndex();
    return solrHelper;
  }

  @Bean
  @ConditionalOnExpression("${populate.customer:true}")
  public CustomerPopulator customerPopulator(){
    return new CustomerPopulator();
  }

  @Bean
  public ObjectMapper jacksonObjectMapper(){
    return new ObjectMapper()
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)

        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        .setPropertyNamingStrategy(CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
        .findAndRegisterModules();
  }

}
