package com.helpscout.dupcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Created by jeff on 4/13/15.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class DupCustomerApp extends RepositoryRestMvcConfiguration {
  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(DupCustomerApp.class, args);
  }

  /**
   * While pure REST dictates a url to a post or put it can be much more convenient for clients
   * to also get the saved object back
   * @param config
   */
  @Override
  protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    super.configureRepositoryRestConfiguration(config);
    config.setReturnBodyOnCreate(true);
    config.setReturnBodyOnUpdate(true);
  }
}
