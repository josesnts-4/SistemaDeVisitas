package com.inter.SistemaDeVisitas.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigLog {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("SistemaDeVisitas");
    }
}
