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
      // CSRF ligado (padrão). Se o seu form não tiver o token, adicione o input hidden (ver login.html).
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        // páginas públicas
        .requestMatchers("/", "/login", "/img/**", "/css/**", "/js/**", "/actuator/health").permitAll()
        // /home exige autenticação
        .requestMatchers(HttpMethod.GET, "/home").authenticated()
        // qualquer outra rota exige login
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")                 // GET da sua página
        .loginProcessingUrl("/login")        // POST do form
        .defaultSuccessUrl("/home", true)    // após logar com sucesso
        .failureUrl("/login?error")          // falha volta pro login com aviso
        .permitAll()
      )
      .logout(l -> l
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );

    return http.build();
  }

  // Usuário de teste (trocar por JDBC/JPA depois)
  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
      User.withUsername("admin@quitandaria.com")
          .password(encoder.encode("123456"))
          .roles("ADMIN")
          .build()
    );
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
