package com.inter.SistemaDeVisitas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class ConfigLog {

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter f = new CommonsRequestLoggingFilter();
    f.setIncludeClientInfo(true);
    f.setIncludeQueryString(true);
    f.setIncludePayload(true);
    f.setMaxPayloadLength(10000);
    return f;
  }
}
