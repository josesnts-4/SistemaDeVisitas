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
      .csrf(csrf -> csrf.disable()) // simples p/ começar; com CSRF, ver passo 3
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/login", "/img/**", "/css/**", "/js/**", "/actuator/health").permitAll()
        .requestMatchers(HttpMethod.GET, "/home").authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")              // GET
        .loginProcessingUrl("/login")     // POST do form
        .defaultSuccessUrl("/home", true)
        .failureUrl("/login?error")
        .permitAll()
      )
      .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

    return http.build();
  }

  // Usuário de teste (trocar por JPA depois)
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
