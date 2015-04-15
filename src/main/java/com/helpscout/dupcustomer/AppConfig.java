package com.helpscout.dupcustomer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.helpscout.dupcustomer.services.CustomerPopulator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES;

/**
 * Configuration for the object mapper rest controllers are using
 */
@Configuration
public class AppConfig {

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
