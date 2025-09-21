// src/main/java/com/inter/SistemaDeVisitas/ConfigLog.java
package com.inter.SistemaDeVisitas;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigLog {
  @Value("${spring.datasource.url:}")  private String url;
  @Value("${spring.datasource.username:}") private String user;

  @PostConstruct
  public void logDs() {
    System.out.println(">> DS URL = " + url);
    System.out.println(">> DS USER = " + user);
  }
}
