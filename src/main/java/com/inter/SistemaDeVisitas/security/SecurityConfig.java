// src/main/java/com/inter/SistemaDeVisitas/security/SecurityConfig.java
package com.inter.SistemaDeVisitas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // CSRF permanece ATIVO (padrão) — seu form já envia o token
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/login", "/img/**", "/css/**", "/js/**", "/actuator/health").permitAll()
        .requestMatchers(HttpMethod.GET, "/home").authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")                 // GET da sua página
        .loginProcessingUrl("/login")        // POST do form (action)
        .defaultSuccessUrl("/home", true)    // pra onde vai após logar
        .failureUrl("/login?error")          // erro → volta com ?error
        .permitAll()
      )
      .logout(l -> l
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );

    return http.build();
  }

  // Usuário de teste (troque por JDBC/JPA depois)
  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
      User.withUsername("admin@quitandaria.com")
          .password(encoder.encode("123456"))
          .roles("ADMIN")
          .build()
    );
  }

  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
