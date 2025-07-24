package com.unitap.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceBundleConfiguration {

  @Value("${locale.basename}")
  private String propertiesBasename;

  @Bean
  public ResourceBundleMessageSource messageSource() {
    val rs = new ResourceBundleMessageSource();
    rs.setBasename(propertiesBasename);
    rs.setDefaultEncoding("UTF-8");
    rs.setUseCodeAsDefaultMessage(true);
    return rs;
  }
}
