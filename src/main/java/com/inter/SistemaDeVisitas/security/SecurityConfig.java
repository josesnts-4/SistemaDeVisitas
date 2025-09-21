package com.inter.SistemaDeVisitas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  @Bean
  PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/login", "/register", "/img/**", "/css/**", "/js/**", "/actuator/health").permitAll()
        .requestMatchers("/admin/**").hasRole("SUPER")
        .requestMatchers(HttpMethod.GET, "/home").authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/home", true)
        .failureUrl("/login?error")
        .permitAll()
      )
      // permite link <a href="/logout"> (GET). Se preferir POST, remova a linha do matcher.
      .logout(l -> l
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );
    return http.build();
  }
}
