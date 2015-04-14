package com.helpscout.dupcustomer;

import com.helpscout.dupcustomer.services.CustomerPopulator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jeff on 4/13/15.
 */
@Configuration
public class AppConfig {

  @Bean
  @ConditionalOnExpression("${populate.customer:true}")
  public CustomerPopulator customerPopulator(){
    return new CustomerPopulator();
  }
}
