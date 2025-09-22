package com.inter.SistemaDeVisitas.security;

import com.inter.SistemaDeVisitas.entity.RoleGroup;
import com.inter.SistemaDeVisitas.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService { // <- sem generics

    private final UserRepository repo;

    public JpaUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // use nome diferente p/ evitar confusão com o User do Spring
        com.inter.SistemaDeVisitas.entity.User appUser = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String role = "ROLE_" + appUser.getRoleGroup().name(); // SUPER/ADMIN/LOJA -> ROLE_*

        // Retorne o UserDetails do Spring, NÃO a entidade JPA
        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .authorities(new SimpleGrantedAuthority(role))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!appUser.isEnabled())
                .build();
    }
}
